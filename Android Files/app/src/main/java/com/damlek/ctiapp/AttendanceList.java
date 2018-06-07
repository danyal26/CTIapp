package com.damlek.ctiapp;

/**
 * Created by danyaldharani on 09/10/2016.
 */

public class AttendanceList {

    private int id;
    private String studentID, firstName, lastName;

    public AttendanceList(int id, String studentID, String firstName, String lastName) {
        this.id = id;
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
