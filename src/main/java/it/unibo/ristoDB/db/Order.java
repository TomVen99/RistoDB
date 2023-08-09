package it.unibo.ristoDB.db;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Order {

    private Date date;
    private Time time;
    private String username;
    private int tableNumber;
    private Optional<Time> closing_time;

    public Order(Date date, Time time, String username, int tableNumber, Optional<Time> closing_time) {
        this.date = date;
        this.time = time;
        this.username = username;
        this.tableNumber = tableNumber;
        this.closing_time = Optional.empty();
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

    public Optional<Time> getClosing_time() {
        return closing_time;
    }
}
