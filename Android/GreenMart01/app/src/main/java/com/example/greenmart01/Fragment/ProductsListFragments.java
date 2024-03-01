package com.example.greenmart01.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.greenmart01.Adapter.ProductsListAddapter;
import com.example.greenmart01.api.RetrofitClient;
import com.example.greenmart01.Adapter.ProductsListAddapter;
import com.example.greenmart01.R;
import com.example.greenmart01.entity.Product;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductsListFragments extends Fragment {

    RecyclerView recyclerView;
    List<Product> productList;

    ProductsListAddapter productsListAddapter;

    public ProductsListFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products_list_fragments, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recyclerView);
        productList = new ArrayList<>();
        Product p = new Product("Cabbage", "Very Good Product", "", 55.26);
        productList.add(p);
        productList.add(p);
        productList.add(p);
        productList.add(p);
        productList.add(p);

        productsListAddapter = new ProductsListAddapter(getContext(), productList);
        recyclerView.setAdapter(productsListAddapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        super.onViewCreated(view, savedInstanceState);
        getAllMovies();


    }

    private void getAllMovies() {
        RetrofitClient.getInstance().getApi().getAllProduct().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().get("status").getAsString().equals("success")) {
                    JsonArray array = response.body().get("data").getAsJsonArray();
                    productList.clear();
                    for (JsonElement element : array) {
                        JsonObject object = element.getAsJsonObject();
                        Product product = new Product();
                        product.setName(object.get("product_name").getAsString());
                        product.setImageUrl(object.get("image").getAsString());
                        product.setDesc(object.get("detail").getAsString());
                        product.setProductId(object.get("product_id").getAsInt());
                        product.setPrice(object.get("Price").getAsDouble());
                        Log.e("product", product.toString());
                        productList.add(product);
                    }
                    productsListAddapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong at Movies List", Toast.LENGTH_SHORT).show();

                Log.e("login", t.getMessage());
            }

        });


    }
}