package it.unibo.ristoDB.db;

import java.util.Map;

public class OrderDetail {
    
    private Map<Product,Integer> productsQuantity;
    private int orderId;

    public OrderDetail(int orderId, Map<Product,Integer> productsQuantity) {
        this.orderId = orderId;
        this.productsQuantity = productsQuantity;
    }

    public Map<Product, Integer> getProductsQuantity() {
        return productsQuantity;
    }

    public int getOrderId() {
        return orderId;
    }

}
