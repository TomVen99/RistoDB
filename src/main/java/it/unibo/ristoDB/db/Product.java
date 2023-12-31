package it.unibo.ristoDB.db;

public class Product {
    
    private int id;
    private String name;
    private float price;
    private String categoryName;

    public Product(int id, String name, float price, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
    
}
