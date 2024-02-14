package com.sunbeaminfo.moviereview.api;

import com.google.gson.JsonObject;
import com.sunbeaminfo.moviereview.entity.Review;
import com.sunbeaminfo.moviereview.entity.Share;
import com.sunbeaminfo.moviereview.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    public static final String BASE_URL="http://192.168.238.21:3000";

    @POST("/user/register")
    public Call<JsonObject> registerUser(@Body User user);

    @POST("/user/login")
    public Call<JsonObject> loginUser(@Body User user);

    @GET("/user/{id}")
    public Call<JsonObject> getUser(@Path("id") int id);

    @GET("/user/")
    public Call<JsonObject> getAllUsers();

    @GET("/movie/")
    public Call<JsonObject> getAllMovies();

    @POST("/movie/moviereview")
    public Call<JsonObject> getReview(@Body Review review);

    @POST("/movie/review")
    public Call<JsonObject> addReview(@Body Review review);

    @PUT("/movie/review")
    public Call<JsonObject> editReview(@Body Review review);

    @GET("/movie/review/{id}")
    public Call<JsonObject> getAllReview(@Path("id") int id);

    @DELETE("/movie/review/{id}")
    public Call<JsonObject> deleteReview(@Path("id") int id);

    @POST("/share/")
    public Call<JsonObject> addShare(@Body Share share);

    @GET("/share/{id}")
    public Call<JsonObject> getSharedReviews(@Path("id") int id);
}

