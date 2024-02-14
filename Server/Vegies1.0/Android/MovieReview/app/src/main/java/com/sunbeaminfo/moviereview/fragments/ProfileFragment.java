package com.sunbeaminfo.moviereview.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sunbeaminfo.moviereview.R;
import com.sunbeaminfo.moviereview.api.RetrofitClient;
import com.sunbeaminfo.moviereview.utility.MovieReviewConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    TextView textFirstName,textLastName,textEmail,textMobile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textFirstName = view.findViewById(R.id.textFirstName);
        textLastName = view.findViewById(R.id.textLastName);
        textEmail = view.findViewById(R.id.textEmail);
        textMobile = view.findViewById(R.id.textMobile);
        getProfile();
    }

    private void getProfile() {
        int userid= getContext().getSharedPreferences(MovieReviewConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                .getInt(MovieReviewConstants.USER_ID,0);
        RetrofitClient.getInstance().getApi().getUser(userid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success")){
                    JsonArray jsonArray = response.body().getAsJsonArray("data");
                    JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                    textFirstName.setText(jsonObject.get("first_name").getAsString());
                    textLastName.setText(jsonObject.get("last_name").getAsString());
                    textEmail.setText(jsonObject.get("email").getAsString());
                    textMobile.setText(jsonObject.get("mobile").getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong while getting the user", Toast.LENGTH_SHORT).show();
            }
        });

    }
}