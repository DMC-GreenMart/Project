package com.example.greenmart01.Adapter;

import android.content.Context;
//import android.content.Intent;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenmart01.R;
import com.example.greenmart01.activities.OrderDetailsActivity;
import com.example.greenmart01.api.API;
import com.example.greenmart01.entity.OrderItem;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder>
{
    Context context;
    List<OrderItem> orderItemsList;

    public OrderListAdapter(Context context, List<OrderItem> orderItemsList) {
        this.context = context;
        this.orderItemsList = orderItemsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_detail_list,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.orderDateTextView.setText("Order Date - "+orderItemsList.get(position).getOrder_date().substring(0, 10));
        holder.priceTextView.setText("Price - "+orderItemsList.get(position).getPrice());

        Glide.with(context)
                .load(orderItemsList.get(position).getImage())
                .into(holder.imageItem);
    }

    @Override
    public int getItemCount() {
        return orderItemsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageItem;
        TextView orderDateTextView,priceTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageItem = itemView.findViewById(R.id.imageItem);
            orderDateTextView = itemView.findViewById(R.id.orderDateTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OrderDetailsActivity.class);
                    intent.putExtra("orderItem",orderItemsList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
