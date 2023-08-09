package it.unibo.ristoDB.db;

import java.sql.Date;
import java.sql.Time;
import java.util.Map;

public class OrderDetail {
    
    private int orderId;
    private Date date;
    private Time time;
    private int quantity;

    public OrderDetail(int orderId, Date date, Time time, int productsQuantity) {
        this.orderId = orderId;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public int getOrderId() {
        return orderId;
    }

}
