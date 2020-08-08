package com.example.sushmita.saloon.model;

public class Salon {

    private String salonId;
    private String salon;
    private String image;

    public Salon() {
    }

    public Salon(String salonId, String salon, String image) {
        this.salonId = salonId;
        this.salon = salon;
        this.image = image;
    }

    public String getSalonId() {
        return salonId;
    }

    public String getSalon() {
        return salon;
    }

    public String getImage() {
        return image;
    }

    public void setSalonId(String salonId) {
        this.salonId = salonId;
    }
}
