package it.unibo.ristoDB.db;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Order {

    private Date date;
    private Time time;
    private String username;
    private int tableNumber;

    public Order(Date date, Time time, String username, int tableNumber) {
        this.date = date;
        this.time = time;
        this.username = username;
        this.tableNumber = tableNumber;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    
}
