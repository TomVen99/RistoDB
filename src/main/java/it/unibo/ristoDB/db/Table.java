package it.unibo.ristoDB.db;

public class Table {

    private int number;
    private boolean busy;
    private int maxPeople;

    public Table(int number, boolean busy, int maxPeople) {
        this.number = number;
        this.maxPeople = maxPeople;
        this.busy = busy;
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
