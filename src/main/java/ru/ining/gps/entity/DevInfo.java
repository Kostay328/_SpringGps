package ru.ining.gps.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DevInfo {
    private int id;
    private int name;
    private String no;
    private int device;
    private Long lats;
    private Long lrts;
    private double lat;
    private double lng;
    private int speed;
    private int course;
    private boolean active;
    private String style;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public String getLats() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(lats));
    }

    public void setLats(Long lats) {
        this.lats = lats;
    }

    public String getLrts() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(lrts));
    }

    public void setLrts(Long lrts) {
        this.lrts = lrts;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getStyle() {
        String res = "vertical-align: middle; text-align: center";
        //86400000 мс в сутках можно умножить для нескольких суток
        if((lats) < (new Date().getTime()-(86400000L)))
            res += "; background-color: lightgray";
        return res;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
