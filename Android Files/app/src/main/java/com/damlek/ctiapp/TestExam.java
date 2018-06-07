package com.damlek.ctiapp;

/**
 * Created by danyaldharani on 20/09/2016.
 */

public class TestExam {

    private String module_name, module_id, date, start_time, end_time, venue;

    public TestExam(String module_name, String module_id, String date, String start_time, String end_time, String venue) {
        this.module_name = module_name;
        this.module_id = module_id;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.venue = venue;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getModule_id() {
        return module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
