package it.unibo.ristoDB.db;

import java.sql.Time;
import java.util.Date;

public class Receipt {
    
    private int id;
    private int orderId;
    private Date date;
    private Time time;

    public Receipt(int id, int orderId, Date date, Time time) {
        this.id = id;
        this.orderId = orderId;
        this.date = date;
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

}
