package com.example.greenmart01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.greenmart01.R;
import com.example.greenmart01.R.id;
import com.example.greenmart01.api.API;
import com.example.greenmart01.entity.OrderItem;

public class OrderDetailsActivity extends AppCompatActivity {
    TextView textProductName,textOrderDate,textStatus;
    ImageView imageProduct;
    RatingBar ratingBar;
    Button btnAddEdit,btnCancel;
    OrderItem orderItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        textProductName = findViewById(R.id.textProductName);
        textOrderDate = findViewById(R.id.textOrderDate);
        textStatus = findViewById(id.textStatus);
        imageProduct = findViewById(R.id.imageProduct);
        ratingBar = findViewById(R.id.ratingBar);
        btnAddEdit = findViewById(R.id.btnAddEdit);
        btnCancel = findViewById(R.id.btnCancel);
        orderItem = (OrderItem) getIntent().getSerializableExtra("orderItem");
        textProductName.setText(orderItem.getProduct_name());
        textOrderDate.setText("Product Date - "+orderItem.getOrder_date().substring(0,10));
        textStatus.setText("Order - "+orderItem.getStatus());

        Glide.with(this).load(orderItem.getImage()).into(imageProduct);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                orderItem.setRating((int) rating);
            }
        });
    }
    public void addRating(View view){
        finish();
    }
    public void cancel(View view){
        finish();
    }

}