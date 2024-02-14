package com.sunbeaminfo.moviereview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sunbeaminfo.moviereview.R;
import com.sunbeaminfo.moviereview.adapter.UserListAdapter;
import com.sunbeaminfo.moviereview.api.RetrofitClient;
import com.sunbeaminfo.moviereview.entity.Share;
import com.sunbeaminfo.moviereview.entity.User;
import com.sunbeaminfo.moviereview.utility.MovieReviewConstants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareReviewActivity extends AppCompatActivity {
    EditText editId;
    RecyclerView recyclerView;
    List<User> userList;
    int userid;
    int reviewid;

    UserListAdapter userListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_review);
        editId = findViewById(R.id.editId);
        recyclerView = findViewById(R.id.recyclerView);
        userList = new ArrayList<>();
        userListAdapter = new UserListAdapter(this,userList);
        recyclerView.setAdapter(userListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        userid = getSharedPreferences(MovieReviewConstants.SHARED_PREFERENCE_FILE_NAME,MODE_PRIVATE)
                .getInt(MovieReviewConstants.USER_ID,0);
        reviewid = getIntent().getIntExtra("id",0);

        getUsers();
    }

    private void getUsers(){
        RetrofitClient.getInstance().getApi().getAllUsers().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success")){
                    JsonArray jsonArray =  response.body().getAsJsonArray("data");
                    userList.clear();
                    for (JsonElement element:jsonArray){
                        JsonObject jsonObject = element.getAsJsonObject();
                        User user = new User();
                        user.setId(jsonObject.get("id").getAsInt());
                        user.setFirst_name(jsonObject.get("first_name").getAsString());
                        user.setLast_name(jsonObject.get("last_name").getAsString());
                        user.setEmail(jsonObject.get("email").getAsString());
                        user.setMobile(jsonObject.get("mobile").getAsString());
                        user.setPassword(jsonObject.get("password").getAsString());
                        userList.add(user);
                    }
                    userListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ShareReviewActivity.this, "Something went wrong while fetching all users", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void share(View view){
    int userIdToShare = Integer.parseInt(editId.getText().toString());
        if(userid != userIdToShare){
            Share share = new Share(reviewid,userIdToShare);
        RetrofitClient.getInstance().getApi().addShare(share).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success"))
                    finish();
                else
                    Toast.makeText(ShareReviewActivity.this, "Userid is invalid", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ShareReviewActivity.this, "something went wrong while adding share", Toast.LENGTH_SHORT).show();
            }
        });
        }
        else
            Toast.makeText(this, "You cannot share review to yourself", Toast.LENGTH_SHORT).show();


    }
}