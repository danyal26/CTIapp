package com.damlek.ctiapp;

/**
 * Created by danyaldharani on 12/09/2016.
 */
public class Payment {

    private int id;
    private String amount, date;

    public Payment(int id, String amount, String date) {
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
