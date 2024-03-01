package com.example.greenmart01.entity;

import java.io.Serializable;

public class PlaceOrderRequest implements Serializable {
    private int user_id;
    private double totalAmt;
    private int addressId;

    public PlaceOrderRequest(){}

    public PlaceOrderRequest(int user_id, double totalAmt, int addressId) {
        this.user_id = user_id;
        this.totalAmt = totalAmt;
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "PlaceOrderRequest{" +
                "user_id=" + user_id +
                ", totalAmt=" + totalAmt +
                ", addressId=" + addressId +
                '}';
    }
    // Constructor, getters, setters
}