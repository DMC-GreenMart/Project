package com.example.greenmart01.api;

import com.example.greenmart01.entity.AddressData;
import com.example.greenmart01.entity.CartItems;
import com.example.greenmart01.entity.PlaceOrderRequest;
import com.example.greenmart01.entity.User;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
        public static final String BASE_URL="http://192.168.86.21:9898";

//    @POST("/user/register")
//    public Call<JsonObject> registerUser(@Body User user);
//
//    @POST("/user/login")
//    public Call<JsonObject> loginUser(@Body User user);
//
//    @GET("/user/{id}")
//    public Call<JsonObject> getUser(@Path("id") int id);
//
//    @GET("/user/")
//    public Call<JsonObject> getAllUsers();
//getAddresById

    @GET("/address/{id}")
    public Call<JsonObject> getAddressById(@Path("id") int id);

    @GET("/product/")
    public Call<JsonObject> getAllProduct();
    @POST("/cart/addcart")
    Call<JsonObject> addToCart(@Body CartItems cartItem);

    @POST("/placeorder")
    Call<JsonObject> addToPlace(@Body PlaceOrderRequest placeOrderRequest);

    @POST("/address")
    Call<JsonObject> addAddress(@Body AddressData addressData);

    //
//    @POST("/movie/moviereview")
//    public Call<JsonObject> getReview(@Body Review review);
//
//    @POST("/movie/review")
//    public Call<JsonObject> addReview(@Body Review review);
//
//    @PUT("/movie/review")
//    public Call<JsonObject> editReview(@Body Review review);
//
    @GET("/cart/{id}")
    public Call<JsonObject> getCartbyId(@Path("id") int id);
//
//    @DELETE("/movie/review/{id}")
//    public Call<JsonObject> deleteReview(@Path("id") int id);
//
//    @POST("/share/")
//    public Call<JsonObject> addShare(@Body Share share);
//
@GET("/user/{id}")
public Call<JsonObject> getUser(@Path("id") int id);
//

    @GET("user/profile/{user_id}")
    Call<User> getUserProfile(@Path("user_id") int user_id);
    @POST("/user/register")
    public Call<JsonObject> registerUser(@Body User user);

    @POST("/user/login")
    public Call<JsonObject> loginUser(@Body User user);


    @GET("/order/myorderDetail/{id}")
    public Call<JsonObject> getAllOrders(@Path("id") int id);
    @DELETE("/cart/{id}")
    public Call<JsonObject> getCart(@Path("id") int id);
}

