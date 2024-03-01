package com.example.greenmart01.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;

import com.example.greenmart01.Adapter.MyPagerAdapter;
import com.example.greenmart01.Adapter.RecyclerViewAdapter;
import com.example.greenmart01.R;
import com.example.greenmart01.entity.DataItem;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager234);
        int[] imageIds = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
        MyPagerAdapter adapter = new MyPagerAdapter(this, imageIds);
        viewPager.setAdapter(adapter);

        final int delay = 2000; // Set the delay between slides in milliseconds
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                int currentPage = viewPager.getCurrentItem();
                int totalPages = imageIds.length;
                int nextPage = (currentPage + 1) % totalPages;
                viewPager.setCurrentItem(nextPage, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, delay, delay);

        recyclerView = findViewById(R.id.recyclerView123);
        getSupportActionBar().setTitle("GreenMart");
        // Sample data as ArrayList
        ArrayList<DataItem> dataList = new ArrayList<>();
        dataList.add(new DataItem("Lefy", R.drawable.lefy));
        dataList.add(new DataItem("Freshy", R.drawable.freshy));
        dataList.add(new DataItem("Fruits", R.drawable.fruits));
        dataList.add(new DataItem("Herbs", R.drawable.hearbs));
        // Add more items with different images as needed

        // Create a GridLayoutManager with 2 columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // Create a RecyclerViewAdapter
        RecyclerViewAdapter rvadapter = new RecyclerViewAdapter(dataList, this);
        recyclerView.setAdapter(rvadapter);
//

    }
}