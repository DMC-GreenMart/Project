package com.example.greenmart01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.greenmart01.R;
import com.example.greenmart01.api.RetrofitClient;
import com.example.greenmart01.entity.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity {

    EditText editUsername, editMail, editPass;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        editUsername = findViewById(R.id.editUsername);
        editMail = findViewById(R.id.editMail);
        editPass = findViewById(R.id.editPass);
        btnRegister = findViewById(R.id.btnRegister);
    }
    public void register(View view) {
        User user = new User();
        user.setUsername(editUsername.getText().toString());
        user.setEmail(editMail.getText().toString());
        user.setPassword(editPass.getText().toString());
        if (user.getEmail().equals("") || user.getPassword().equals(""))
            Toast.makeText(this, "email or password cannot be empty", Toast.LENGTH_SHORT).show();
        else {
            RetrofitClient.getInstance().getApi().registerUser(user).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.body().get("status").getAsString().equals("success")){
                        Toast.makeText(RegisterUserActivity.this, "user registered successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        if (response.body().get("error").getAsJsonObject().get("errno").getAsInt() == 1062)
                            Toast.makeText(RegisterUserActivity.this, "email already exists", Toast.LENGTH_SHORT).show();
                        if (response.body().get("error").getAsJsonObject().get("errno").getAsInt() == 1406)
                            Toast.makeText(RegisterUserActivity.this, "mobile number is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(RegisterUserActivity.this, "Something went wrong while registration", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void cancel(View view){
        finish();
    }
}
