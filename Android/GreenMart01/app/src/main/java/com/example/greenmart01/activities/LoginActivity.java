package com.example.greenmart01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenmart01.R;
import com.example.greenmart01.api.RetrofitClient;
import com.example.greenmart01.entity.User;
import com.example.greenmart01.utility.GreenMartConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    TextView textRegister;
    CheckBox checkboxRememberMe;

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        textRegister=findViewById(R.id.textRegister);
        checkboxRememberMe = findViewById(R.id.checkboxRememberMe);
    }

    public void Login(View view){
        User user = new User();
        user.setEmail(editEmail.getText().toString());
        user.setPassword(editPassword.getText().toString());
        if (checkboxRememberMe.isChecked()){
            getSharedPreferences(GreenMartConstants.SHARED_PREFERENCE_FILE_NAME,MODE_PRIVATE)
                    .edit()
                    .putBoolean(GreenMartConstants.LOGIN_STATUS,true)
                    .apply();
        }
        RetrofitClient.getInstance().getApi().loginUser(user).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().get("status").getAsString().equals("success")){
                    JsonArray array =response.body().get("data").getAsJsonArray();
                    if (array.size()!=0){
                        int id = array.get(0).getAsJsonObject().get("user_id").getAsInt();
                        getSharedPreferences(GreenMartConstants.SHARED_PREFERENCE_FILE_NAME,MODE_PRIVATE)
                                .edit()
                                .putInt(GreenMartConstants.USER_ID,id)
                                .apply();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }
                    else
                        Toast.makeText(LoginActivity.this,"email or password is wrong",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Something went wrong at the Login",Toast.LENGTH_SHORT).show();
                Log.e("Api", t.getMessage());

            }
        });
    }
    public void gotoRegister(View view){
        Intent intent=new Intent(this, RegisterUserActivity.class);
        startActivity(intent);
    }
}

