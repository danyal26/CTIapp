package com.damlek.ctiapp;

/**
 * Created by danyaldharani on 10/09/2016.
 */
public class Module {

    private String moduleID, moduleName, moduleLength, programID, lecturerID, nqfLevel, cost, programLevel;

    public Module(String moduleID, String moduleName, String moduleLength, String programID, String lecturerID, String nqfLevel, String cost, String programLevel) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.moduleLength = moduleLength;
        this.programID = programID;
        this.lecturerID = lecturerID;
        this.nqfLevel = nqfLevel;
        this.cost = cost;
        this.programLevel = programLevel;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleLength() {
        return moduleLength;
    }

    public void setModuleLength(String moduleLength) {
        this.moduleLength = moduleLength;
    }

    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }

    public String getNqfLevel() {
        return nqfLevel;
    }

    public void setNqfLevel(String nqfLevel) {
        this.nqfLevel = nqfLevel;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getProgramLevel() {
        return programLevel;
    }

    public void setProgramLevel(String programLevel) {
        this.programLevel = programLevel;
    }
}
