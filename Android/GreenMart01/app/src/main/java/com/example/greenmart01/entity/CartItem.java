package com.example.greenmart01.entity;

public class CartItem {

    public  CartItem(){}

    private int cartId;
    private int productId;
    private String productName;
    private double productPrice;
    private double totalPrice;
    private String image;
    private int quantity;

    public CartItem(int cartId, int productId, String productName, double productPrice, double totalPrice, String image, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.image = image;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}