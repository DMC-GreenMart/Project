package com.example.greenmart01.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenmart01.R;
import com.example.greenmart01.activities.AddToCardActivity;
import com.example.greenmart01.entity.Product;

import java.util.List;

public class ProductsListAddapter extends  RecyclerView.Adapter<ProductsListAddapter.MyViewHolder> {

    Context context;
    List<Product> productList;

    public ProductsListAddapter(Context context, List<Product> list)
    {
        this.context = context;
        this.productList = list;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_view,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textViewName.setText(product.getName());
        holder.textViewPrice.setText(String.format("Price: $%.2f", product.getPrice()));
        // Load image using your preferred image loading library like Glide or Picasso
//         For example,
        Log.e("images" , product.getImageUrl());
         Glide.with(holder.imageView.getContext()).load(product.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewName;
        TextView textViewPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddToCardActivity.class);
                    intent.putExtra("product", productList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }

}
