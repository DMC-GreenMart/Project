package com.example.greenmart01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.greenmart01.R;
import com.example.greenmart01.api.API;
import com.example.greenmart01.api.RetrofitClient;
import com.example.greenmart01.entity.CartItems;
import com.example.greenmart01.entity.Product;
import com.example.greenmart01.utility.GreenMartConstants;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToCardActivity extends AppCompatActivity {


    TextView textName;
    ImageView imageMovie;

    TextView price;
    TextView description ;
    Button btnAdd;
    TextView tvQuantity;

    Button btnPlus;
    Button btnMinus;
    Product product;
    double currentPrice;
    int quantity = 1;

// com.example.greenmart01.entity.Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_card);
        textName = findViewById(R.id.textName);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        imageMovie = findViewById(R.id.imageMovie);
        btnAdd = findViewById(R.id.btnAddEdit);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        tvQuantity = findViewById(R.id.tvQuantity);



        product = (Product) getIntent().getSerializableExtra("product");
        textName.setText(product.getName());
        price.setText(product.getPrice()+"");
        description.setText(product.getDesc());
//        Glide.with(this).load(API.BASE_URL+"/"+movie.getImag()).into(imageMovie);
        Glide.with(imageMovie.getContext()).load(product.getImageUrl()).into(imageMovie);
//        review.setUserid(getSharedPreferences(MovieReviewConstants.SHARED_PREFERENCE_FILE_NAME,MODE_PRIVATE)
//                .getInt(MovieReviewConstants.USER_ID,0));
        currentPrice = product.getPrice();
        updatePrice();

    }

    public void increasePrice(View view) {
        quantity++;
        updateQuantity();
        updatePrice();
    }

    public void decreasePrice(View view) {
        if (quantity > 1) {
            quantity--;
            updateQuantity();
            updatePrice();
        }
    }

    private void updateQuantity() {
        tvQuantity.setText(String.valueOf(quantity));
    }

    private void updatePrice() {
        // Update the price TextView with the current price and quantity
        double totalPrice = currentPrice * quantity;
        price.setText(String.format("Total Price: $%.2f", totalPrice));
    }

    public  void addToCart(View view)
    {
        int userId = getApplicationContext().getSharedPreferences(GreenMartConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                .getInt(GreenMartConstants.USER_ID,0);

        btnAdd.setEnabled(false);
        btnPlus.setEnabled(false);
        btnMinus.setEnabled(false);
        btnAdd.setText("Added To Cart");
        CartItems cartItem = new CartItems();
        cartItem.setUserId(userId);
        cartItem.setProductId(product.getProductId());
        cartItem.setQuantity(quantity);
        cartItem.setPrice(currentPrice);
//        Toast.makeText(AddToCardActivity.this, cartItem.toString(), Toast.LENGTH_SHORT).show();
        API api = RetrofitClient.getInstance().getApi();
        Call<JsonObject> jsonObjectCall = null;
        jsonObjectCall = api.addToCart(cartItem);
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success"))
                    Toast.makeText(AddToCardActivity.this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
//                    finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("cart", t.getMessage());
                Toast.makeText(AddToCardActivity.this, "Something went wrong while add/edit Cartttt", Toast.LENGTH_SHORT).show();
            }
        });


    }

}