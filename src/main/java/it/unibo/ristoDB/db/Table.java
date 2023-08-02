package it.unibo.ristoDB.db;

public class Table {

    private int number;
    private boolean busy;
    private int maxPeople;

    public Table(int number, int maxPeople) {
        this.number = number;
        this.maxPeople = maxPeople;
        busy = false;
    }

    public int getNumber() {
        return number;
    }

    public boolean isBusy() {
        return busy;
    }

    public int getMaxPeople() {
        return maxPeople;
    }   
    
}