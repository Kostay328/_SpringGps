package ru.ining.gps.entity;

public class CarMillage {
    private int id;
    private int car;
    private String no;
    private String name;
    private long startt;
    private long endt;
    private double mileage;
    private int maxspeed;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartt() {
        return startt;
    }

    public void setStartt(long startt) {
        this.startt = startt;
    }

    public long getEndt() {
        return endt;
    }

    public void setEndt(long endt) {
        this.endt = endt;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public int getMaxspeed() {
        return maxspeed;
    }

    public void setMaxspeed(int maxspeed) {
        this.maxspeed = maxspeed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", no=" + no +
                ", mileage='" + mileage + '\'' +
                '}';
    }
}
