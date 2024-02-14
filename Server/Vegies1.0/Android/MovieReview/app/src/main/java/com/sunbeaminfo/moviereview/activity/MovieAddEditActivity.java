package com.sunbeaminfo.moviereview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sunbeaminfo.moviereview.R;
import com.sunbeaminfo.moviereview.api.API;
import com.sunbeaminfo.moviereview.api.RetrofitClient;
import com.sunbeaminfo.moviereview.entity.Movie;
import com.sunbeaminfo.moviereview.entity.Review;
import com.sunbeaminfo.moviereview.utility.MovieReviewConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieAddEditActivity extends AppCompatActivity {
    TextView textTitle,textDate;
    ImageView imageMovie;
    RatingBar ratingBar;
    EditText editReview;
    Button btnAddEdit;

    Movie movie;
    Review review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_add_edit);
        textTitle = findViewById(R.id.textTitle);
        textDate = findViewById(R.id.textDate);
        imageMovie = findViewById(R.id.imageMovie);
        ratingBar = findViewById(R.id.ratingBar);
        editReview = findViewById(R.id.editReview);
        btnAddEdit = findViewById(R.id.btnAddEdit);
        movie = (Movie) getIntent().getSerializableExtra("movie");
        textTitle.setText(movie.getTitle());
        textDate.setText("Release Date - "+movie.getRelease_date());
        Glide.with(this).load(API.BASE_URL+"/"+movie.getImag()).into(imageMovie);
        review = new Review();
        review.setMovieid(movie.getId());
        review.setUserid(getSharedPreferences(MovieReviewConstants.SHARED_PREFERENCE_FILE_NAME,MODE_PRIVATE)
                .getInt(MovieReviewConstants.USER_ID,0));
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                review.setRating((int) rating);
            }
        });
        getReview();
    }

    private void getReview(){
        RetrofitClient.getInstance().getApi().getReview(review).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success")){
                    JsonArray jsonArray = response.body().getAsJsonArray("data");
                    if(jsonArray.size()!=0){
                        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                        editReview.setText(jsonObject.get("review").getAsString());
                        ratingBar.setRating(jsonObject.get("rating").getAsInt());
                        review.setId(jsonObject.get("id").getAsInt());
                        btnAddEdit.setText("Edit Review");
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MovieAddEditActivity.this, "Something went wrong while getting review", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addEditReview(View view){
        review.setReview(editReview.getText().toString());
        API api = RetrofitClient.getInstance().getApi();
        Call<JsonObject> jsonObjectCall = null;

        if(btnAddEdit.getText().equals("Add Review"))
            jsonObjectCall = api.addReview(review);
        if(btnAddEdit.getText().equals("Edit Review"))
            jsonObjectCall = api.editReview(review);

        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success"))
                    finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MovieAddEditActivity.this, "Something went wrong while add/edit review", Toast.LENGTH_SHORT).show();
            }
        });
    }
}