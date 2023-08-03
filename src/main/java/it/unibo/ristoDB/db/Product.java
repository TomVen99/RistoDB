package it.unibo.ristoDB.db;

public class Product {
    
    private int id;
    private String name;
    private float price;
    private int categoryId;

    public Product(int id, String name, float price, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
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
