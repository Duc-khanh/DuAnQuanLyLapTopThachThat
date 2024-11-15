package org.example.laptopthachthat.Admin;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;

public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String describe;
    private Boolean stock;
    private ImageView image;

    public Product(int id, String name, double price, int quantity, String describe, Boolean stock, ImageView image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.describe = describe;
        this.stock = stock;
        this.image = image;
    }

    public Product(Product product) {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Boolean getStock() {
        return stock;
    }

    public void setStock(Boolean stock) {
        this.stock = stock;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public boolean isAvailable() {
        return stock != null && stock;
    }

    public void setAvailable(boolean available) {
        this.stock = available;
    }

    public boolean isStock() {
        return stock != null && stock;
    }


    public ObservableValue<Double> totalPriceProperty() {
        return new SimpleDoubleProperty(price * quantity).asObject();
    }


}
