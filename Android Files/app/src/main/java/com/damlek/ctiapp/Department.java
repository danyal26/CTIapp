package com.damlek.ctiapp;

/**
 * Created by danyaldharani on 22/09/2016.
 */

public class Department {

    private String id, deptName, coordniator, type;

    public Department(String id, String deptName, String coordniator, String type) {
        this.id = id;
        this.deptName = deptName;
        this.coordniator = coordniator;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCoordniator() {
        return coordniator;
    }

    public void setCoordniator(String coordniator) {
        this.coordniator = coordniator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
