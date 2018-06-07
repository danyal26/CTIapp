package com.damlek.ctiapp;

/**
 * Created by danyaldharani on 29/09/2016.
 */

public class ClassTimetable {

    private String id, modID, roomNo, sessionID, modName, fName, lName;

    public ClassTimetable(String id, String modID, String roomNo, String sessionID, String modName, String fName, String lName) {
        this.id = id;
        this.modID = modID;
        this.roomNo = roomNo;
        this.sessionID = sessionID;
        this.modName = modName;
        this.fName = fName;
        this.lName = lName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModID() {
        return modID;
    }

    public void setModID(String modID) {
        this.modID = modID;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
}
