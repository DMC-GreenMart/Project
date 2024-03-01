package com.example.greenmart01.entity;

import java.io.Serializable;

public class CartItems implements Serializable {

   public   CartItems(){}
    private int user_id;


    private int product_id;


    private int quantity;


    private double price;

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public int getProductId() {
        return product_id;
    }

    public void setProductId(int productId) {
        this.product_id = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CartItems{" +
                "user_id=" + user_id +
                ", product_id=" + product_id +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public CartItems(int userId, int productId, int quantity, double price) {
        this.user_id = userId;
        this.product_id = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
