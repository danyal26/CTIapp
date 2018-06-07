package com.damlek.ctiapp;

/**
 * Created by danyaldharani on 24/08/2016.
 */
public class News {

    private String id, departmentName, message, date, time;

    public News(String id, String departmentName, String message, String date, String time) {
        this.id = id;
        this.departmentName = departmentName;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
