package com.example.greenmart01.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private  int productId;
    private String name;

    private String desc;
    private String imageUrl;
    private double price;

    public Product(String name, String desc, String imageUrl, double price) {
        this.name = name;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Product() {

    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    // Constructor, getters, and setters
}
