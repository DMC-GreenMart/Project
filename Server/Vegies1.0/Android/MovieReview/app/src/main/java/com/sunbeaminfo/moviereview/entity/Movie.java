package com.sunbeaminfo.moviereview.entity;

import android.util.Log;

import java.io.Serializable;

public class Movie implements Serializable {
    private  int id;
    private String title;
    private String imag;
    private String release_date;

    public Movie() {
    }

    public Movie(String title, String imag, String release_date) {
        this.title = title;
        this.imag = imag;
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImag() {
        return imag;
    }

    public void setImag(String imag) {
        this.imag = imag;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        release_date = release_date.split("T")[0];
        Log.e("Movie",release_date);
        this.release_date = release_date;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", release_date='" + release_date + '\'' +
                '}';
    }
}
