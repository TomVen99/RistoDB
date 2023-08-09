package it.unibo.ristoDB.db;

import java.sql.Date;
import java.sql.Time;

public class Workshift {

    private Date date;
    private String dayMoment;
    private int start_time;
    private int finish_time;
    
    public Workshift(Date date, String dayMoment, int start_time, int finish_time) {
        this.date = date;
        this.dayMoment = dayMoment;
        this.start_time = start_time;
        this.finish_time = finish_time;
    }

    public Date getDate() {
        return date;
    }

    public String getDayMoment() {
        return dayMoment;
    }

    public int getStart_time() {
        return start_time;
    }

    public int getFinish_time() {
        return finish_time;
    }
}
