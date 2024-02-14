package com.sunbeaminfo.moviereview.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sunbeaminfo.moviereview.R;
import com.sunbeaminfo.moviereview.adapter.MainFragmentAdapter;
import com.sunbeaminfo.moviereview.utility.MovieReviewConstants;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    Toolbar toolbar;

    MainFragmentAdapter mainFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolBar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);
        mainFragmentAdapter = new MainFragmentAdapter(this);
        viewPager2.setAdapter(mainFragmentAdapter);
        setSupportActionBar(toolbar);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setIcon(R.drawable.movie_list);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.review_list);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.share);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.profile);
                        break;
                }
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // to check for if remeber me is checked then only show the logout menu button
        if (getSharedPreferences(MovieReviewConstants.SHARED_PREFERENCE_FILE_NAME, MODE_PRIVATE)
                .getBoolean(MovieReviewConstants.LOGIN_STATUS, false))
            menu.add("logout").setIcon(R.drawable.logout).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        getSharedPreferences(MovieReviewConstants.SHARED_PREFERENCE_FILE_NAME, MODE_PRIVATE)
                .edit()
                .putBoolean(MovieReviewConstants.LOGIN_STATUS, false)
                .apply();
        finish();
        return super.onOptionsItemSelected(item);
    }
}