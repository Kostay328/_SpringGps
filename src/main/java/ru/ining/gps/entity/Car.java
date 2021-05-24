package ru.ining.gps.entity;

public class Car {
    private Integer id;
    private Integer model;
    private String no;
    private String name;
    private Byte enabled;
    private Long crtts;
    private Integer crtuser;
    private Long modts;
    private Integer moduser;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModel() {
        return this.model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getNo() {
        return this.no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    public Long getCrtts() {
        return this.crtts;
    }

    public void setCrtts(Long crtts) {
        this.crtts = crtts;
    }

    public Integer getCrtuser() {
        return this.crtuser;
    }

    public void setCrtuser(Integer crtuser) {
        this.crtuser = crtuser;
    }

    public Long getModts() {
        return this.modts;
    }

    public void setModts(Long modts) {
        this.modts = modts;
    }

    public Integer getModuser() {
        return this.moduser;
    }

    public void setModuser(Integer moduser) {
        this.moduser = moduser;
    }
}
