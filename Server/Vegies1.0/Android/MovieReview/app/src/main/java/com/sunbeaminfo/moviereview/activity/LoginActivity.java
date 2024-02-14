package com.sunbeaminfo.moviereview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sunbeaminfo.moviereview.R;
import com.sunbeaminfo.moviereview.api.RetrofitClient;
import com.sunbeaminfo.moviereview.entity.User;
import com.sunbeaminfo.moviereview.utility.MovieReviewConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText editEmail,editPassword;
    CheckBox checkboxRememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        checkboxRememberMe = findViewById(R.id.checkboxRememberMe);
    }

    public void login(View view){
        User user = new User();
        user.setEmail(editEmail.getText().toString());
        user.setPassword(editPassword.getText().toString());
        if(checkboxRememberMe.isChecked()){
            getSharedPreferences(MovieReviewConstants.SHARED_PREFERENCE_FILE_NAME,MODE_PRIVATE)
                    .edit()
                    .putBoolean(MovieReviewConstants.LOGIN_STATUS,true)
                    .apply();
        }
        RetrofitClient.getInstance().getApi().loginUser(user).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success")){
                    JsonArray array = response.body().get("data").getAsJsonArray();
                    if(array.size()!=0){
                        int id = array.get(0).getAsJsonObject().get("id").getAsInt();
                        getSharedPreferences(MovieReviewConstants.SHARED_PREFERENCE_FILE_NAME,MODE_PRIVATE)
                                .edit()
                                .putInt(MovieReviewConstants.USER_ID,id)
                                .apply();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    else
                        Toast.makeText(LoginActivity.this, "email or password is wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something went wrong at the Login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void register(View view){
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}