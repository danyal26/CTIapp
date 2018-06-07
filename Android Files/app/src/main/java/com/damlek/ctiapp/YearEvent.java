package com.damlek.ctiapp;

/**
 * Created by danyaldharani on 23/09/2016.
 */

public class YearEvent {

    private String id, type, date, details;

    public YearEvent(String id, String type, String date, String details) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
