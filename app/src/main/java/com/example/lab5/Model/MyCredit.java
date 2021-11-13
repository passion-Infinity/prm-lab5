package com.example.lab5.Model;

import java.io.Serializable;

public class MyCredit implements Serializable {

    private String id;
    private String title;
    private String trackTime;
    private double cash;
    private int typeTransaction;
    private String description;

    public MyCredit() {
    }

    public MyCredit(String id, String title, String trackTime, double cash, int typeTransaction, String description) {
        this.id = id;
        this.title = title;
        this.trackTime = trackTime;
        this.cash = cash;
        this.typeTransaction = typeTransaction;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrackTime() {
        return trackTime;
    }

    public void setTrackTime(String trackTime) {
        this.trackTime = trackTime;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public int getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(int typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
