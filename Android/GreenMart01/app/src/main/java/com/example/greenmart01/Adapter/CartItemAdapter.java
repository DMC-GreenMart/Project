package com.example.greenmart01.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenmart01.R;
import com.example.greenmart01.entity.CartItem;
import com.example.greenmart01.entity.CartItems;
import com.example.greenmart01.listner.CartListListner;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    private List<CartItem> cartItemList;

    CartListListner cartListner;
    public CartItemAdapter(List<CartItem> cartItemList, CartListListner cartListner) {
        this.cartItemList = cartItemList;
        this.cartListner = cartListner;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartview, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);

        // Set data to views
        holder.productNameTextView.setText(cartItem.getProductName());
        holder.quantityTextView.setText("Quantity: " + cartItem.getQuantity());
        holder.productPriceTextView.setText("Price: $" + cartItem.getProductPrice());
        holder.totalPriceTextView.setText("Total: $" + (cartItem.getProductPrice() * cartItem.getQuantity()));

        // You may load image here using Glide or any other image loading library
         Glide.with(holder.itemView).load(cartItem.getImage()).into(holder.productImageView);


    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView quantityTextView;
        TextView productPriceTextView;
        TextView totalPriceTextView;
        ImageView productImageView;
        Button button;
        // ImageView productImageView; // Uncomment this if you have an ImageView for product image

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.textView_product_name);
            quantityTextView = itemView.findViewById(R.id.textView_quantity);
            productPriceTextView = itemView.findViewById(R.id.textView_product_price);
            totalPriceTextView = itemView.findViewById(R.id.textView_total_price);
             productImageView = itemView.findViewById(R.id.imageView_product);
             button = itemView.findViewById(R.id.removeBtn);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartListner.onDeleteClicked(cartItemList.get(getAdapterPosition()).getCartId());

                }
            });


            // Uncomment this if you have an ImageView for product image
        }
    }
}
