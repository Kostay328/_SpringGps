package ru.ining.gps.db;

public class Car {
    private Integer id;
    private Integer car;
    private String no;
    private Long startt;
    private Long endt;
    private Double millage;

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCar() { return this.car; }
    public void setCar(Integer car) { this.car = car; }

    public String getNo() { return this.no; }
    public void setNo(String no) {this.no = no;}

    public Long getStartt() { return this.startt; }
    public void setStartt(Long startt) { this.startt = startt; }

    public Long getEndt() { return this.endt; }
    public void setEndt(Long endt) { this.endt = endt; }

    public Double getMillage() { return this.millage; }
    public void setMillage(Double millage) { this.millage = millage; }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", no=" + no +
                ", millage='" + millage + '\'' +
                '}';
    }
}
