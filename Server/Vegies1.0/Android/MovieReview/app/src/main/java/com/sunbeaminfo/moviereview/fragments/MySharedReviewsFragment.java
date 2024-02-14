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
import com.sunbeaminfo.moviereview.adapter.MyShareReviewListAdapter;
import com.sunbeaminfo.moviereview.api.RetrofitClient;
import com.sunbeaminfo.moviereview.entity.Review;
import com.sunbeaminfo.moviereview.utility.MovieReviewConstants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MySharedReviewsFragment extends Fragment {

    RecyclerView recyclerView;
    List<Review> reviewList;
    MyShareReviewListAdapter myShareReviewListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_shared_reviews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        reviewList = new ArrayList<>();
        myShareReviewListAdapter = new MyShareReviewListAdapter(getContext(),reviewList);
        recyclerView.setAdapter(myShareReviewListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
    }

    @Override
    public void onStart() {
        super.onStart();
        getAllSharedReviews();
    }

    private void getAllSharedReviews(){
        int id = getContext().getSharedPreferences(MovieReviewConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                        .getInt(MovieReviewConstants.USER_ID,0);
        RetrofitClient.getInstance().getApi().getSharedReviews(id).enqueue(new Callback<JsonObject>() {
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
                    myShareReviewListAdapter.notifyDataSetChanged();
             }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong for fetching all shared reviews", Toast.LENGTH_SHORT).show();
            }
        });
    }
}