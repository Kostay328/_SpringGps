package ru.ining.gps.entity;

import java.util.Date;

public class DocElem {
    private int Tcpoa;          //N
    private Date Actdte;        //Дата
    private String Dep;         //Шаблон
    private String Template;    //Отдел


    private String Crtpsndessign;  //Автор
    private Date Crtdtesign;

    private String Agrpsndessign;  //Утвержден
    private Date Agrdtesign;

    private String Apppsndessign;  //Согласованно
    private Date Appdtesign;

    private String Exepsndessign;  //Исполнен
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

    public String getCrtpsndessign() {
        return Crtpsndessign;
    }

    public void setCrtpsndessign(String crtpsndessign) {
        Crtpsndessign = crtpsndessign;
    }

    public Date getCrtdtesign() {
        return Crtdtesign;
    }

    public void setCrtdtesign(Date crtdtesign) {
        Crtdtesign = crtdtesign;
    }

    public String getAgrpsndessign() {
        return Agrpsndessign;
    }

    public void setAgrpsndessign(String agrpsndessign) {
        Agrpsndessign = agrpsndessign;
    }

    public Date getAgrdtesign() {
        return Agrdtesign;
    }

    public void setAgrdtesign(Date agrdtesign) {
        Agrdtesign = agrdtesign;
    }

    public String getApppsndessign() {
        return Apppsndessign;
    }

    public void setApppsndessign(String apppsndessign) {
        Apppsndessign = apppsndessign;
    }

    public Date getAppdtesign() {
        return Appdtesign;
    }

    public void setAppdtesign(Date appdtesign) {
        Appdtesign = appdtesign;
    }

    public String getExepsndessign() {
        return Exepsndessign;
    }

    public void setExepsndessign(String exepsndessign) {
        Exepsndessign = exepsndessign;
    }

    public Date getExedtesign() {
        return Exedtesign;
    }

    public void setExedtesign(Date exedtesign) {
        Exedtesign = exedtesign;
    }

    public DocElem(int tcpoa, Date actdte, String dep, String template, String crtpsndessign, Date crtdtesign, String agrpsndessign, Date agrdtesign, String apppsndessign, Date appdtesign, String exepsndessign, Date exedtesign) {
        Tcpoa = tcpoa;
        Actdte = actdte;
        Dep = dep;
        Template = template;
        Crtpsndessign = crtpsndessign;
        Crtdtesign = crtdtesign;
        Agrpsndessign = agrpsndessign;
        Agrdtesign = agrdtesign;
        Apppsndessign = apppsndessign;
        Appdtesign = appdtesign;
        Exepsndessign = exepsndessign;
        Exedtesign = exedtesign;
    }
}
