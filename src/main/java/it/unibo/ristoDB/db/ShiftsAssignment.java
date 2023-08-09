package it.unibo.ristoDB.db;

import java.sql.Date;

public class ShiftsAssignment {
    
    private String username;
    private Date date;
    private String dayMoment;

    public ShiftsAssignment(String username, Date date, String dayMoment) {
        this.username = username;
        this.date = date;
        this.dayMoment = dayMoment;
    }

    public String getUsername() {
        return username;
    }

    public Date getDate() {
        return date;
    }

    public String getDayMoment() {
        return dayMoment;
    }

}
