package com.example.greenmart01.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.greenmart01.R;
import com.example.greenmart01.Adapter.MainFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        toolbar = findViewById(R.id.toolBar1);
        tabLayout = findViewById(R.id.tabLayout1);
        viewPager2 = findViewById(R.id.viewpager2);

        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(this);
        viewPager2.setAdapter(mainFragmentAdapter);

//        setSupportActionBar(toolbar);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)
                {
                    case   0  :
                        tab.setIcon(R.drawable.eggplant_svgrepo_com);
                        break;

                    case 1:
                        tab.setIcon(R.drawable.cart_alt_1_svgrepo_com);
                        break;
                    case 2 :
                        tab.setIcon(R.drawable.free_shipping_svgrepo_com);
                        break;

                    case 3 :
                        tab.setIcon(R.drawable.profile_user_svgrepo_com);
                        break;

                }

            }
        }).attach();
    }
}