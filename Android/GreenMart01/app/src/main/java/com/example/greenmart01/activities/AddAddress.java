package com.example.greenmart01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.greenmart01.R;
import com.example.greenmart01.api.API;
import com.example.greenmart01.api.RetrofitClient;
import com.example.greenmart01.entity.AddressData;
import com.example.greenmart01.utility.GreenMartConstants;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddress extends AppCompatActivity {

    private EditText streetEditText, cityEditText, stateEditText, zipCodeEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        // Initialize EditText fields
        streetEditText = findViewById(R.id.street_edittext);
        cityEditText = findViewById(R.id.city_edittext);
        stateEditText = findViewById(R.id.state_edittext);
        zipCodeEditText = findViewById(R.id.zip_code_edittext);


        // Initialize submit button
        submitButton = findViewById(R.id.submit_button);



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve text from EditText fields
                String street = streetEditText.getText().toString().trim();
                String city = cityEditText.getText().toString().trim();
                String state = stateEditText.getText().toString().trim();
                String zipCode = zipCodeEditText.getText().toString().trim();


                // Validate input fields
                if (street.isEmpty() || city.isEmpty() || state.isEmpty() || zipCode.isEmpty()) {
                    Toast.makeText(AddAddress.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with submission (e.g., send data to server, etc.)
                    // Here, we just display a toast message with the entered data

                 // call addAddress()
                    int user_id = getApplicationContext().getSharedPreferences(GreenMartConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                            .getInt(GreenMartConstants.USER_ID,0);

                    AddressData addressData = new AddressData(user_id , street , city , state , zipCode);
                    addAddress(addressData);

                    String address = street + ", " + city + ", " + state + " - " + zipCode;
                    Toast.makeText(AddAddress.this, "Submitted: " + address, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public  void  addAddress(AddressData addressData)
    {

        API api = RetrofitClient.getInstance().getApi();
        Call<JsonObject> jsonObjectCall = null;
        jsonObjectCall = api.addAddress(addressData);
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success"))
                {
                    Toast.makeText(AddAddress.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("cart", t.getMessage());
                Toast.makeText(AddAddress.this, "Something went wrong while ", Toast.LENGTH_SHORT).show();
            }
        });




    }

}