package com.sunbeaminfo.moviereview.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sunbeaminfo.moviereview.R;
import com.sunbeaminfo.moviereview.activity.ShareReviewActivity;
import com.sunbeaminfo.moviereview.entity.Review;
import com.sunbeaminfo.moviereview.entity.User;
import com.sunbeaminfo.moviereview.listner.MovieReviewListListner;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    Context context;
    List<User> userList;
    MovieReviewListListner movieReviewListListner;

    public UserListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_user, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userList.get(position);
        holder.textUserId.setText("userId - "+user.getId());
        holder.textFirstName.setText(user.getFirst_name());
        holder.textLastName.setText(user.getLast_name());
        holder.textEmail.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textUserId, textFirstName, textLastName,textEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textUserId = itemView.findViewById(R.id.textUserId);
            textFirstName = itemView.findViewById(R.id.textFirstName);
            textLastName = itemView.findViewById(R.id.textLastName);
            textEmail = itemView.findViewById(R.id.textEmail);

        }
    }
}
