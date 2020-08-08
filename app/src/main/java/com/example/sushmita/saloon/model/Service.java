package com.example.sushmita.saloon.model;

public class Service {

    private String serviceId;
    private String serviceName;
    private boolean serviceChecked;
    private double serviceCharges;
    private boolean hideCheckBox;

    public Service() {
    }

    public Service(String serviceName, double serviceCharges, boolean hideCheckBox) {
        this.serviceName = serviceName;
        this.serviceCharges = serviceCharges;
        this.hideCheckBox = hideCheckBox;
    }

    public boolean isHideCheckBox() {
        return hideCheckBox;
    }

    public Service(String serviceName, boolean serviceChecked, double serviceCharges, boolean hideCheckBox) {
        this.serviceName = serviceName;
        this.serviceChecked = serviceChecked;
        this.serviceCharges = serviceCharges;
        this.hideCheckBox = hideCheckBox;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public boolean isServiceChecked() {
        return serviceChecked;
    }

    public double getServiceCharges() {
        return serviceCharges;
    }
}
