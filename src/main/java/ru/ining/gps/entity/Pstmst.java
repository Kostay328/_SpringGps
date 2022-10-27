package ru.ining.gps.entity;


import java.sql.Timestamp;

public class Pstmst {

  private String pst;
  private String des;
  private String des80;
  private String entby;
  private String lstchby;
  private java.sql.Timestamp entdte;
  private java.sql.Timestamp lstchdte;
  private String rcdsts;

  public Pstmst(String pst, String des, String des80, String entby, String lstchby, Timestamp entdte, Timestamp lstchdte, String rcdsts) {
    this.pst = pst;
    this.des = des;
    this.des80 = des80;
    this.entby = entby;
    this.lstchby = lstchby;
    this.entdte = entdte;
    this.lstchdte = lstchdte;
    this.rcdsts = rcdsts;
  }

  public String getPst() {
    return pst;
  }

  public void setPst(String pst) {
    this.pst = pst;
  }


  public String getDes() {
    return des;
  }

  public void setDes(String des) {
    this.des = des;
  }


  public String getDes80() {
    return des80;
  }

  public void setDes80(String des80) {
    this.des80 = des80;
  }


  public String getEntby() {
    return entby;
  }

  public void setEntby(String entby) {
    this.entby = entby;
  }


  public String getLstchby() {
    return lstchby;
  }

  public void setLstchby(String lstchby) {
    this.lstchby = lstchby;
  }


  public java.sql.Timestamp getEntdte() {
    return entdte;
  }

  public void setEntdte(java.sql.Timestamp entdte) {
    this.entdte = entdte;
  }


  public java.sql.Timestamp getLstchdte() {
    return lstchdte;
  }

  public void setLstchdte(java.sql.Timestamp lstchdte) {
    this.lstchdte = lstchdte;
  }


  public String getRcdsts() {
    return rcdsts;
  }

  public void setRcdsts(String rcdsts) {
    this.rcdsts = rcdsts;
  }

}
