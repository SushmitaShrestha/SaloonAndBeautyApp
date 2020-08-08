package com.example.sushmita.saloon.model;

import java.util.ArrayList;
import java.util.Map;

public class History {
    private String userId;
    private String salonId;
    private String salonName;
    private Map<String, String> map;
    private String time;
    private String date;

    public History(String userId, String salonId, String salonName, Map<String, String> map, String time, String date) {
        this.userId = userId;
        this.salonId = salonId;
        this.salonName = salonName;
        this.map = map;
        this.time = time;
        this.date = date;
    }

    public History() {
    }

    public String getUserId() {
        return userId;
    }

    public String getSalonId() {
        return salonId;
    }

    public String getSalonName() {
        return salonName;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}