package com.example.greenmart01.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenmart01.entity.DataItem;
import com.example.greenmart01.activities.HomeActivity;
import com.example.greenmart01.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<DataItem> dataList;
    private Context context;

    public RecyclerViewAdapter(ArrayList<DataItem> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem item = dataList.get(position);
        holder.textView.setText(item.getText());
        holder.imageView.setImageResource(item.getImageResource());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                DataItem item = dataList.get(position);
                Intent intent = new Intent(context, HomeActivity.class);
                // Pass data to HomeActivity using intent extras
                intent.putExtra("text", item.getText());
                intent.putExtra("imageResource", item.getImageResource());
                context.startActivity(intent);
            }
        }

    }
}


