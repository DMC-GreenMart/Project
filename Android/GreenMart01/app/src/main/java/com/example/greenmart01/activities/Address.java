package com.example.greenmart01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.greenmart01.R;
import com.example.greenmart01.api.API;
import com.example.greenmart01.api.RetrofitClient;
import com.example.greenmart01.entity.CartItem;
import com.example.greenmart01.entity.PlaceOrderRequest;
import com.example.greenmart01.utility.GreenMartConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Address extends AppCompatActivity {

    private Spinner addressSpinner;
    private RadioGroup paymentMethodRadioGroup;
    private RadioButton codRadioButton;
    private RadioButton creditCardRadioButton;
    private Button checkoutButton;
    Button addAddress;

    private TextView totalAmountTextView;
    ArrayList<String> addressList;
    ArrayAdapter addressAdapter;

    String selectedAddress;
    Double totalAmt ;

    int addressId ;
    ListView addressListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        // Initialize views
        paymentMethodRadioGroup = findViewById(R.id.payment_method_radio_group);
        codRadioButton = findViewById(R.id.cod_radio_button);
        creditCardRadioButton = findViewById(R.id.credit_card_radio_button);
        checkoutButton = findViewById(R.id.checkout_button);
        totalAmountTextView = findViewById(R.id.total_amount_textview);
   addAddress = findViewById(R.id.add_address_button);
        totalAmt = (Double) getIntent().getSerializableExtra("totalAmt");
        Log.e("totalamt", totalAmt+"");
        totalAmountTextView.setText("Total Amount "+totalAmt+"");
        addressListView = findViewById(R.id.address_listview);
        addressList = new ArrayList<>();

        // Fetch addresses
        getAddress();

          addressAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addressList);

        // Set adapter to ListView
        addressListView.setAdapter(addressAdapter);

        // Set item click listener for ListView
        addressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Reset color for previously selected item
                for (int i = 0; i < addressList.size(); i++) {
                    if (i == position) {
                        selectedAddress =(String) addressAdapter.getItem(position);
                        View listItem = addressListView.getChildAt(i);
                        if (listItem != null) {
                            listItem.setBackgroundColor(Color.GREEN);
                        }
                    } else {
                        View listItem = addressListView.getChildAt(i);
                        if (listItem != null) {
                            listItem.setBackgroundColor(Color.TRANSPARENT);
                        }
                    }
                }
            }



        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Address.this, AddAddress.class); // Use Address.this to refer to the outer class context
                startActivity(intent); //
            }
        });
        // Set up checkout button listener
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected address
                addressId = Integer.parseInt(selectedAddress.substring(0,1));

                int userId = getApplicationContext().getSharedPreferences(GreenMartConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                        .getInt(GreenMartConstants.USER_ID,0);

//                totalAmt
          // adddress id

               placeOrder(userId  , totalAmt , addressId);

                // Get selected payment method
                String paymentMethod = codRadioButton.isChecked() ? "Cash on Delivery" : "Credit Card";

                // Perform checkout action (you can replace this with your own logic)
                checkout(selectedAddress, paymentMethod);
            }
        });
    }


 public  void placeOrder(int userId , double totalAmt  , int addressId)
 {
     PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(userId,totalAmt , addressId);
     API api = RetrofitClient.getInstance().getApi();
     Call<JsonObject> jsonObjectCall = null;
     jsonObjectCall = api.addToPlace(placeOrderRequest);
     jsonObjectCall.enqueue(new Callback<JsonObject>() {
         @Override
         public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
             if(response.body().get("status").getAsString().equals("success"))
             {
                 Toast.makeText(Address.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                 finish();
             }

         }

         @Override
         public void onFailure(Call<JsonObject> call, Throwable t) {
             Log.e("cart", t.getMessage());
             Toast.makeText(Address.this, "Something went wrong while add/edit Cartttt", Toast.LENGTH_SHORT).show();
         }
     });


 }

    @Override
    protected void onStart() {
        super.onStart();
        getAddress();
    }

    public void onResume() {
        super.onResume();
        // Refresh the data every time the fragment resumes
        getAddress();
    }

    public  void  getAddress()
    {
        int userid =   getApplicationContext().getSharedPreferences(GreenMartConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
            .getInt(GreenMartConstants.USER_ID,0);

        RetrofitClient.getInstance().getApi().getAddressById(userid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success")){
                    addressList.clear();
                    JsonArray jsonArray = response.body().getAsJsonArray("data");
                    addressList.clear();
                    for (JsonElement element : jsonArray){
                        JsonObject jsonObject = element.getAsJsonObject();

                      String addressId = jsonObject.get("address_id").getAsString();
                        String street =  jsonObject.get("street").getAsString();
                        String  city  =jsonObject.get("city").getAsString();
                        String  state = jsonObject.get("state").getAsString();

                        String address = addressId +" - "+street + " " + city + "  " + state;

                        addressList.add(address);


                    }

                    addressListView.setAdapter(addressAdapter);

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Address.this, "Something went wrong while getting all user reviews", Toast.LENGTH_SHORT).show();
            }
        });




    }


    // Method to perform checkout action
    private void checkout(String address, String paymentMethod) {
        // Dummy implementation, replace with your actual checkout logic
        String message = "Checkout Address: " + address + "\nPayment Method: " + paymentMethod+"id "+addressId ;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}