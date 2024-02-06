package com.mechamate.entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@NoArgsConstructor
public class Device{
    private String operatingSystem;
    private String browser;
    SimpleDateFormat lastAccessDate;
    private String lastAccessIP;

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getBrowser() {
        return browser;
    }

    public SimpleDateFormat getLastAccessDate() {
        return lastAccessDate;
    }

    public String getLastAccessIP() {
        return lastAccessIP;
    }
}
