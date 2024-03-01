package com.example.greenmart01.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenmart01.R;
import com.example.greenmart01.activities.LoginActivity;
import com.example.greenmart01.api.RetrofitClient;
import com.example.greenmart01.utility.GreenMartConstants;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyProfile extends Fragment {

    private Button btnEditProfile;
    private Button btnLogout;


    TextView textUserName,textEmail,textPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textUserName = view.findViewById(R.id.textUserName);
        textEmail =  view.findViewById(R.id.textEmail);
        textPassword = view.findViewById(R.id.textPassword);
        getProfile();
    }

    public void onStart() {
        super.onStart();
        getProfile();
    }

    private void getProfile(){
        int user_id = getContext().getSharedPreferences(GreenMartConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                .getInt(GreenMartConstants.USER_ID,0);

        RetrofitClient.getInstance().getApi().getUser(user_id).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {



                if(response.body().get("status").getAsString().equals("success")){
                    JsonArray jsonArray = response.body().getAsJsonArray("data");
                    JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                    textUserName.setText(jsonObject.get("username").getAsString());
                    textEmail.setText(jsonObject.get("email").getAsString());
                    textPassword.setText(jsonObject.get("password").getAsString());
                    Log.e("profilee", textUserName+""+textPassword+""+textPassword);

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong while getting the user", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);


        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Place Order" button click
                btnEditProfile();
            }

            private void btnEditProfile() {
            }
        });

        btnLogout.setOnClickListener(new View.
                OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Place Order" button click
                btnLogout();
            }

            private void btnLogout() {

                getContext().getSharedPreferences(GreenMartConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                        .edit()
                        .clear()
                        .apply();

                // Redirect user to login screen or perform any other action as needed
                // For example, you can navigate to the login screen
                 Intent intent = new Intent(getContext(), LoginActivity.class);
                 startActivity(intent);
                 getActivity().finish(); // Finish the current activity if needed

                // You can also simply display a message indicating successful logout
                Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

            }

        });
        return  view;

    }




}