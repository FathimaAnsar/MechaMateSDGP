package com.mechamate.service;

import java.util.ArrayList;

public class UserProfile {
    private int id;
    private String username;
    private int status;
    private String password;
    private String fName;
    private String lName;
    private ArrayList<Vehicle> vehicles;
    private Session currentSession;
    private ArrayList<Session> otherSessions;
    private Log log;

    // Constructors
    public UserProfile() {
        // Default constructor
    }
    public enum ProfileStatusEnum {
        ACTIVE, INACTIVE, SUSPENDED;
    }
    public String getProfileStatusDescription() {
        switch (status) {
            case 1:
                return "Active";
            case 2:
                return "Inactive";
            case 3:
                return "Suspended";
            // Add more cases for other enum values if needed
            default:
                return "Unknown";
        }
    }

    public UserProfile(int id, int status, String username, String password, String fName, String lName,
                       ArrayList<Vehicle> vehicles, Session currentSession, ArrayList<Session> otherSessions,
                        Log log) {
        this.id = id;
        this.status = status;
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.vehicles = vehicles;
        this.currentSession = currentSession;
        this.otherSessions = otherSessions;
        this.log = log;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public ArrayList<Session> getOtherSessions() {
        return otherSessions;
    }

    public void setOtherSessions(ArrayList<Session> otherSessions) {
        this.otherSessions = otherSessions;
    }


    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    // Method to add a vehicle to the profile
    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

}

