package ru.ining.gps.entity;

import java.util.Date;

public class DocElem {
    private int Tcpoa;          //N
    private Date Actdte;        //Дата
    private String Dep;         //Шаблон
    private String Template;    //Отдел


    private String Crtpsnsign;  //Автор
    private Date Crtdtesign;

    private String Agrpsnsign;  //Утвержден
    private Date Agrdtesign;

    private String Apppsnsign;  //Согласованно
    private Date Appdtesign;

    private String Exepsnsign;  //Исполнен
    private Date Exedtesign;

    public int getTcpoa() {
        return Tcpoa;
    }

    public void setTcpoa(int tcpoa) {
        Tcpoa = tcpoa;
    }

    public Date getActdte() {
        return Actdte;
    }

    public void setActdte(Date actdte) {
        Actdte = actdte;
    }

    public String getDep() {
        return Dep;
    }

    public void setDep(String dep) {
        Dep = dep;
    }

    public String getTemplate() {
        return Template;
    }

    public void setTemplate(String template) {
        Template = template;
    }

    public String getCrtpsnsign() {
        return Crtpsnsign;
    }

    public void setCrtpsnsign(String crtpsnsign) {
        Crtpsnsign = crtpsnsign;
    }

    public Date getCrtdtesign() {
        return Crtdtesign;
    }

    public void setCrtdtesign(Date crtdtesign) {
        Crtdtesign = crtdtesign;
    }

    public String getAgrpsnsign() {
        return Agrpsnsign;
    }

    public void setAgrpsnsign(String agrpsnsign) {
        Agrpsnsign = agrpsnsign;
    }

    public Date getAgrdtesign() {
        return Agrdtesign;
    }

    public void setAgrdtesign(Date agrdtesign) {
        Agrdtesign = agrdtesign;
    }

    public String getApppsnsign() {
        return Apppsnsign;
    }

    public void setApppsnsign(String apppsnsign) {
        Apppsnsign = apppsnsign;
    }

    public Date getAppdtesign() {
        return Appdtesign;
    }

    public void setAppdtesign(Date appdtesign) {
        Appdtesign = appdtesign;
    }

    public String getExepsnsign() {
        return Exepsnsign;
    }

    public void setExepsnsign(String exepsnsign) {
        Exepsnsign = exepsnsign;
    }

    public Date getExedtesign() {
        return Exedtesign;
    }

    public void setExedtesign(Date exedtesign) {
        Exedtesign = exedtesign;
    }

    public DocElem(int tcpoa, Date actdte, String dep, String template, String crtpsnsign, Date crtdtesign, String agrpsnsign, Date agrdtesign, String apppsnsign, Date appdtesign, String exepsnsign, Date exedtesign) {
        Tcpoa = tcpoa;
        Actdte = actdte;
        Dep = dep;
        Template = template;
        Crtpsnsign = crtpsnsign;
        Crtdtesign = crtdtesign;
        Agrpsnsign = agrpsnsign;
        Agrdtesign = agrdtesign;
        Apppsnsign = apppsnsign;
        Appdtesign = appdtesign;
        Exepsnsign = exepsnsign;
        Exedtesign = exedtesign;
    }
}
