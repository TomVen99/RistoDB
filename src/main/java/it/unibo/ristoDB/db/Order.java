package it.unibo.ristoDB.db;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Order {

    private int id;
    private Date date;
    private Time time;
    private List<Product> products;

    public Order(int id, Date date, Time time, List<Product> products) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public List<Product> getProducts() {
        return products;
    }
    
}
