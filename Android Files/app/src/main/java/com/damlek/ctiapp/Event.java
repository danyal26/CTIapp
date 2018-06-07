package com.damlek.ctiapp;

/**
 * Created by danyaldharani on 12/09/2016.
 */
public class Event {

    private String eventID, eventName, time, date, departmentName, details, venue;

    public Event(String eventID, String eventName, String time, String date, String departmentName, String details, String venue) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.time = time;
        this.date = date;
        this.departmentName = departmentName;
        this.details = details;
        this.venue = venue;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
