package it.unibo.ristoDB.db;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Order {

    private int id;
    private Date date;
    private Time time;
    private List<Product> products;
    private int tableNumber;
    private int idCamerirere;

    public Order(int id, Date date, Time time, List<Product> products, int tableNumber, int idCamerirere) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.products = products;
        this.tableNumber = tableNumber;
        this.idCamerirere = idCamerirere;
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
