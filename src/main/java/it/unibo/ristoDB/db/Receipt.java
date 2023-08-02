package it.unibo.ristoDB.db;

import java.sql.Time;
import java.util.Date;

public class Receipt {
    
    private int id;
    private Date date;
    private Time time;

    public Receipt(int id, Date date, Time time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

}
