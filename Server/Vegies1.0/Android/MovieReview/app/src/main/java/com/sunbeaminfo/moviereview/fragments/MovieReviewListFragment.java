package com.sunbeaminfo.moviereview.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sunbeaminfo.moviereview.R;
import com.sunbeaminfo.moviereview.adapter.MovieReviewListAdapter;
import com.sunbeaminfo.moviereview.api.RetrofitClient;
import com.sunbeaminfo.moviereview.entity.Review;
import com.sunbeaminfo.moviereview.listner.MovieReviewListListner;
import com.sunbeaminfo.moviereview.utility.MovieReviewConstants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieReviewListFragment extends Fragment implements MovieReviewListListner {

    List<Review> reviewList;
    RecyclerView recyclerView;

    MovieReviewListAdapter movieReviewListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_review_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        reviewList = new ArrayList();
        movieReviewListAdapter = new MovieReviewListAdapter(getContext(),reviewList,this);
        recyclerView.setAdapter(movieReviewListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
    }

    @Override
    public void onStart() {
        super.onStart();
        getReviews();
    }

    private void getReviews(){
        int userid = getContext().getSharedPreferences(MovieReviewConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                .getInt(MovieReviewConstants.USER_ID,0);
        RetrofitClient.getInstance().getApi().getAllReview(userid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success")){
                    JsonArray jsonArray = response.body().getAsJsonArray("data");
                    reviewList.clear();
                    for (JsonElement element : jsonArray){
                        JsonObject jsonObject = element.getAsJsonObject();
                        Review review = new Review();
                        review.setId(jsonObject.get("id").getAsInt());
                        review.setMovieid(jsonObject.get("movieid").getAsInt());
                        review.setReview(jsonObject.get("review").getAsString());
                        review.setRating(jsonObject.get("rating").getAsInt());
                        review.setUserid(jsonObject.get("userid").getAsInt());
                        reviewList.add(review);
                    }
                    movieReviewListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong while getting all user reviews", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClicked(int id) {
        RetrofitClient.getInstance().getApi().deleteReview(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success"))
                    getReviews();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong while delete", Toast.LENGTH_SHORT).show();
            }
        });
    }
}