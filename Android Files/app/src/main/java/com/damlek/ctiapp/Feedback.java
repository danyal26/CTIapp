package com.damlek.ctiapp;

/**
 * Created by danyaldharani on 25/09/2016.
 */

public class Feedback {

    private int id;
    private String deptID, studentID, message, deptName;

    public Feedback(int id, String deptID, String studentID, String message, String deptName) {
        this.id = id;
        this.deptID = deptID;
        this.studentID = studentID;
        this.message = message;
        this.deptName = deptName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
