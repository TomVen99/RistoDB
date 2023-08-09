package it.unibo.ristoDB.view;

public class ReceiptsOrder {

    private String productName;
    private int quantity;
    private float price;

    public ReceiptsOrder(String productName, int quantity, float price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    

}
