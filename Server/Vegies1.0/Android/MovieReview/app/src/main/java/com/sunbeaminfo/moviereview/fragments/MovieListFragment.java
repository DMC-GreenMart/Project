package com.sunbeaminfo.moviereview.fragments;

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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sunbeaminfo.moviereview.R;
import com.sunbeaminfo.moviereview.adapter.MovieListAdapter;
import com.sunbeaminfo.moviereview.api.RetrofitClient;
import com.sunbeaminfo.moviereview.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListFragment extends Fragment {
    RecyclerView recyclerView;
    List<Movie> movieList;

    MovieListAdapter movieListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        movieList = new ArrayList<>();
        movieListAdapter = new MovieListAdapter(getContext(),movieList);
        recyclerView.setAdapter(movieListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        super.onViewCreated(view, savedInstanceState);
        getAllMovies();
    }

    private void getAllMovies(){
        RetrofitClient.getInstance().getApi().getAllMovies().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success")){
                    JsonArray array = response.body().get("data").getAsJsonArray();
                    movieList.clear();
                    for(JsonElement element:array){
                        JsonObject object = element.getAsJsonObject();
                        Movie movie = new Movie();
                        movie.setId(object.get("id").getAsInt());
                        movie.setTitle(object.get("title").getAsString());
                        movie.setImag(object.get("image").getAsString());
                        movie.setRelease_date(object.get("release_date").getAsString());
                        movieList.add(movie);
                    }
                    movieListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(getContext(), "Something went wrong at Movies List", Toast.LENGTH_SHORT).show();

                Log.e("login" , "api error ");
            }

        });
    }


}