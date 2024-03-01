package com.example.greenmart01.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenmart01.Adapter.CartItemAdapter;
import com.example.greenmart01.R;
import com.example.greenmart01.activities.Address;
import com.example.greenmart01.activities.OrderPlacementActivity;
import com.example.greenmart01.api.RetrofitClient;
import com.example.greenmart01.entity.CartItem;
import com.example.greenmart01.entity.CartItems;
import com.example.greenmart01.listner.CartListListner;
import com.example.greenmart01.utility.GreenMartConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment implements CartListListner {

    private Button placeOrderButton;

    RecyclerView recyclerView;

     CartItemAdapter cartItemAdapter;
    TextView totalAmountTextView;
    List<CartItem> cartItems;

    double totalAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView  = view.findViewById(R.id.recyclerView_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Other initialization code...
        recyclerView = view.findViewById(R.id.recyclerView_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize cartItemList (replace this with your actual list of cart items)
//         cartItems = new ArrayList<>();
//        CartItem c = new CartItem( 0 , 1 , "Potato" , 150.00 , 300.00 ,"https://shorturl.at/hsIO8" , 2 );
//        CartItem d = new CartItem( 0 , 2 , "Lady Finger" , 20.00 , 40.00 ,"https://rb.gy/3qh22s" , 2 );
//        CartItem e = new CartItem( 0 , 3 , "Spinach" , 45.00 , 40.00 ,"https://shorturl.at/adtzT" , 1);
//        cartItems.add(c);
//        cartItems.add(d);
//        cartItems.add(e);

        // Initialize adapter with the cartItemList
        cartItems = new ArrayList<>();
        getCart();
        cartItemAdapter = new CartItemAdapter(cartItems ,this);
        recyclerView.setAdapter(cartItemAdapter);


        placeOrderButton = view.findViewById(R.id.button_place_order);
        totalAmountTextView = view.findViewById(R.id.textView_total_amount);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Place Order" button click
                totalAmount = calculateTotalAmount(cartItems);
                   Intent intent = new Intent(getActivity(), Address.class);

                    intent.putExtra("totalAmt", totalAmount);
                Log.e("totalamtcart", totalAmount+"");

                   startActivity(intent);
                   getActivity().finish();

//                placeOrder();
            }
        });

         totalAmount = calculateTotalAmount(cartItems);
        totalAmountTextView.setText(String.format("%.2f", totalAmount));
        // Assuming string resource is defined for formatting
        return view;
    }
    public void onStart() {
        super.onStart();
        getCart();
    }

    public void onResume() {
        super.onResume();
        // Refresh the data every time the fragment resumes
        getCart();
    }


    public  void  getCart()
    {

        int userId = getContext().getSharedPreferences(GreenMartConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                .getInt(GreenMartConstants.USER_ID,0);
        RetrofitClient.getInstance().getApi().getCartbyId(userId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success")){
                    JsonArray jsonArray = response.body().getAsJsonArray("data");
                    cartItems.clear();
                    for (JsonElement element : jsonArray){
                        JsonObject jsonObject = element.getAsJsonObject();
                        CartItem cart = new CartItem();


                        cart.setCartId(jsonObject.get("cart_id").getAsInt());
                       cart.setProductId(jsonObject.get("product_id").getAsInt());
                        cart.setProductPrice(jsonObject.get("ProductPrice").getAsDouble());
                        cart.setTotalPrice(jsonObject.get("TotalPrice").getAsDouble());
                        cart.setImage(jsonObject.get("image").getAsString());
                        cart.setQuantity(jsonObject.get("quantity").getAsInt());
                        cart.setProductName(jsonObject.get("product_name").getAsString());
//                        review.setUserid(jsonObject.get("product_name").getAsInt());
                        cartItems.add(cart);


                    }
                    cartItemAdapter.notifyDataSetChanged();
                    double totalAmount = calculateTotalAmount(cartItems);
                    totalAmountTextView.setText(String.valueOf(totalAmount));
                    cartItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong while getting all user reviews", Toast.LENGTH_SHORT).show();
            }
        });






    }

    private double calculateTotalAmount(List<CartItem> cartItemList) {
        double total = 0.0;
        for (CartItem item : cartItemList) {
            total += item.getProductPrice() * item.getQuantity(); // Assuming each cart item has price and quantity properties
        }
        return total;
    }



    private void placeOrder() {
        // Logic to handle placing the order goes here...
        // going to new Activity for taking adderess and enabling cash on Deleivery options
//        placeOrderButton.setText("");
    }


    @Override
    public void onDeleteClicked(int id) {

        RetrofitClient.getInstance().getApi().getCart(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success"))
                    getCart();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong while delete", Toast.LENGTH_SHORT).show();
            }
        });


    }
}