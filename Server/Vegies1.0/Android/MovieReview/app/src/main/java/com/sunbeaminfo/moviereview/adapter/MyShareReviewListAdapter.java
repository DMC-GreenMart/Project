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
import com.sunbeaminfo.moviereview.listner.MovieReviewListListner;

import java.util.List;

public class MyShareReviewListAdapter extends RecyclerView.Adapter<MyShareReviewListAdapter.MyViewHolder>{
    Context context;
    List<Review> reviewList;

    public MyShareReviewListAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_share_review,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Review review =  reviewList.get(position);
        holder.textMovieId.setText("movieId - "+review.getMovieid());
        holder.textReview.setText(review.getReview());
        holder.textRating.setText("rating - "+review.getRating());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textMovieId,textReview,textRating;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textMovieId = itemView.findViewById(R.id.textMovieId);
            textReview = itemView.findViewById(R.id.textReview);
            textRating = itemView.findViewById(R.id.textRating);




        }
    }
}
