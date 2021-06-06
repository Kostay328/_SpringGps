package ru.ining.gps.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Marker {
    private int name;
    private String time;
    private int speed;
    private int deviceNo;
    private Double lat;
    private Double lng;
    private int course;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(int deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }



    @Override
    public String toString() {
        return ("new google.maps.Marker({position: {lat: " + lat + ", lng: " + lng + "}, title: \"" + name + ", " + time + ", " + speed + ", " + deviceNo + "\", label: \"" + name + "\", icon: {path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW, scale: 6, fillColor: \"red\", fillOpacity: 1, strokeWeight: 0.1, rotation: " + course + "}, map}); ");
    }

    public Marker(int name, String time, int speed, int deviceNo, Double lat, Double lng, int course) {
        this.name = name;
        this.time = time;
        this.speed = speed;
        this.deviceNo = deviceNo;
        this.lat = lat;
        this.lng = lng;
        this.course = course;
    }
}
