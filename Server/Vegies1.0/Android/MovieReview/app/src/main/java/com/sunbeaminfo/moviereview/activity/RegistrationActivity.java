package com.sunbeaminfo.moviereview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sunbeaminfo.moviereview.R;
import com.sunbeaminfo.moviereview.api.API;
import com.sunbeaminfo.moviereview.api.RetrofitClient;
import com.sunbeaminfo.moviereview.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    EditText editFirstName,editLastName,editEmail,editPassword,editMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editMobile = findViewById(R.id.editMobile);
    }

    public void register(View view){
        User user = new User();
        user.setFirst_name(editFirstName.getText().toString());
        user.setLast_name(editLastName.getText().toString());
        user.setEmail(editEmail.getText().toString());
        user.setPassword(editPassword.getText().toString());
        user.setMobile(editMobile.getText().toString());
        if(user.getEmail().equals("") || user.getPassword().equals(""))
            Toast.makeText(this, "email or password cannot be empty", Toast.LENGTH_SHORT).show();
        else {
           RetrofitClient.getInstance().getApi().registerUser(user).enqueue(new Callback<JsonObject>() {
               @Override
               public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success")){
                    finish();
                }
                else {
                    if (response.body().get("error").getAsJsonObject().get("errno").getAsInt() == 1062)
                        Toast.makeText(RegistrationActivity.this, "email already exists", Toast.LENGTH_SHORT).show();
                    if (response.body().get("error").getAsJsonObject().get("errno").getAsInt() == 1406)
                        Toast.makeText(RegistrationActivity.this, "mobile number is incorrect", Toast.LENGTH_SHORT).show();
                }
               }

               @Override
               public void onFailure(Call<JsonObject> call, Throwable t) {
                   Toast.makeText(RegistrationActivity.this, "Something went wrong while registration", Toast.LENGTH_SHORT).show();
               }
           });
        }
    }

    public void cancel(View view){
        finish();
    }
}