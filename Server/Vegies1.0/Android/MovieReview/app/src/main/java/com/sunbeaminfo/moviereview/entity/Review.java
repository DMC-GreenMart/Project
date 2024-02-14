package com.sunbeaminfo.moviereview.entity;

import java.io.Serializable;

public class Review implements Serializable {
    private int id;
    private int movieid;
    private String review;
    private int rating;
    private int userid;

    public Review() {
    }

    public Review(int movieid, String review, int rating, int userid) {
        this.movieid = movieid;
        this.review = review;
        this.rating = rating;
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Review{" +
                "review='" + review + '\'' +
                ", rating=" + rating +
                '}';
    }
}
