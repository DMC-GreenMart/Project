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

public class MovieReviewListAdapter extends RecyclerView.Adapter<MovieReviewListAdapter.MyViewHolder>{
    Context context;
    List<Review> reviewList;
    MovieReviewListListner movieReviewListListner;

    public MovieReviewListAdapter(Context context, List<Review> reviewList,MovieReviewListListner movieReviewListListner) {
        this.context = context;
        this.reviewList = reviewList;
        this.movieReviewListListner = movieReviewListListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_movie_review,null);
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
        ImageView imageDelete,imageShare;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textMovieId = itemView.findViewById(R.id.textMovieId);
            textReview = itemView.findViewById(R.id.textReview);
            textRating = itemView.findViewById(R.id.textRating);
            imageDelete = itemView.findViewById(R.id.imageDelete);
            imageShare = itemView.findViewById(R.id.imageShare);
            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieReviewListListner.onDeleteClicked(reviewList.get(getAdapterPosition()).getId());
                }
            });

            imageShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShareReviewActivity.class);
                    intent.putExtra("id",reviewList.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
