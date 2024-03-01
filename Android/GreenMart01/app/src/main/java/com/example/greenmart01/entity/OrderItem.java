package com.example.greenmart01.entity;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private int order_id;
    private String order_date;
    private String product_name;
    private String image;
    private Double price;
    private int quantity;
    private Double total_price;
    private int rating;
    private String status;

    public OrderItem(){

    }

    public OrderItem(int order_id, String order_date, String product_name, String image, Double price, int quantity, Double total_price, int rating, String status) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.product_name = product_name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.total_price = total_price;
        this.rating = rating;
        this.status = status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
