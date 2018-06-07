package com.damlek.ctiapp;

/**
 * Created by DAMLEK GROUP.
 */

public class Assignment {

    private String assID, modID, assNo, startDate, endDate, modName;

    public Assignment(String assID, String modID, String assNo, String startDate, String endDate, String modName) {
        this.assID = assID;
        this.modID = modID;
        this.assNo = assNo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.modName = modName;
    }

    public String getAssID() {
        return assID;
    }

    public void setAssID(String assID) {
        this.assID = assID;
    }

    public String getModID() {
        return modID;
    }

    public void setModID(String modID) {
        this.modID = modID;
    }

    public String getAssNo() {
        return assNo;
    }

    public void setAssNo(String assNo) {
        this.assNo = assNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }
}
