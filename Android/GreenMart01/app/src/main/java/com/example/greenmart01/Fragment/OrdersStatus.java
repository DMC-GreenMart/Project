package com.example.greenmart01.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.greenmart01.Adapter.OrderListAdapter;
import com.example.greenmart01.R;
import com.example.greenmart01.api.RetrofitClient;
import com.example.greenmart01.entity.OrderItem;
import com.example.greenmart01.utility.GreenMartConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersStatus extends Fragment {
    RecyclerView recyclerView;
    List<OrderItem> orderItemList;
    OrderListAdapter orderListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        orderItemList = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(getContext(),orderItemList);
        recyclerView.setAdapter(orderListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        super.onViewCreated(view, savedInstanceState);
        getAllOrders();
    }

    private void getAllOrders(){
        int userId = getContext().getSharedPreferences(GreenMartConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                .getInt(GreenMartConstants.USER_ID,0);

        RetrofitClient.getInstance().getApi().getAllOrders(userId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success")){
                    JsonArray array = response.body().get("data").getAsJsonArray();
                    orderItemList.clear();
                    for(JsonElement element:array){
                        JsonObject object = element.getAsJsonObject();
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrder_id(object.get("order_id").getAsInt());
                        orderItem.setOrder_date(object.get("order_date").getAsString());
                        orderItem.setProduct_name(object.get("product_name").getAsString());
                        orderItem.setImage(object.get("image").getAsString());
                        orderItem.setPrice(object.get("price").getAsDouble());
                        orderItem.setQuantity(object.get("quantity").getAsInt());
                        orderItem.setTotal_price(object.get("total_price").getAsDouble());
                        orderItem.setStatus(object.get("status").getAsString());
                        orderItemList.add(orderItem);
                    }
                    orderListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong at Movies List", Toast.LENGTH_SHORT).show();
            }
        });
    }
}