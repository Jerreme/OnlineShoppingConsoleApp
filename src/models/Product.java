package models;

public class Product {
    private final int key;
    private final String productName;
    private final int price;

    public Product(int key, String productName, int price) {
        this.key = key;
        this.productName = productName;
        this.price = price;
    }

    public int getKey() {
        return key;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }
}