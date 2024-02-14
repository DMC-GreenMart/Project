package com.sunbeaminfo.moviereview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sunbeaminfo.moviereview.R;
import com.sunbeaminfo.moviereview.api.API;
import com.sunbeaminfo.moviereview.utility.MovieReviewConstants;

public class SplashActivity extends AppCompatActivity {
    ImageView imageSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageSplash= findViewById(R.id.imageSplash);
        //imageSplash.startAnimation(AnimationUtils.loadAnimation(this,R.anim.zoom));
        Glide.with(this).load(API.BASE_URL+"/clapperboard.gif").into(imageSplash);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    if(getSharedPreferences(MovieReviewConstants.SHARED_PREFERENCE_FILE_NAME,MODE_PRIVATE)
                            .getBoolean(MovieReviewConstants.LOGIN_STATUS,false))
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    else
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                    finish();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}