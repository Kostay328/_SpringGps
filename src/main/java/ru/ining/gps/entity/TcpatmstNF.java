package ru.ining.gps.entity;


import java.sql.Timestamp;

public class TcpatmstNF {

  private String tcpat;
  private String des;
  private String header;
  private String text;
  private String pctstamp;
  private String wordstamp;
  private String linkstamp;
  private String entby;
  private String lstchgby;
  private java.sql.Timestamp entdte;
  private java.sql.Timestamp lstchgdte;
  private String rcdsts;
  private String crtdoctyp;
  private String agrdoctyp;
  private String aprdoctyp;
  private String exedoctyp;
  private String lifetime;
  private String savetime;
  private String Comment;
  private int Newtmp;
  private Boolean Multisign;


  public String getComment() {
    return Comment;
  }

  public void setComment(String comment) {
    Comment = comment;
  }

  public int getNewtmp() {
    return Newtmp;
  }

  public void setNewtmp(int newtmp) {
    Newtmp = newtmp;
  }

  public Boolean getMultisign() {
    return Multisign;
  }

  public void setMultisign(Boolean multisign) {
    Multisign = multisign;
  }

  public String getTcpat() {
    return tcpat;
  }

  public void setTcpat(String tcpat) {
    this.tcpat = tcpat;
  }


  public String getDes() {
    return des;
  }

  public void setDes(String des) {
    this.des = des;
  }


  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }


  public String getPctstamp() {
    return pctstamp;
  }

  public void setPctstamp(String pctstamp) {
    this.pctstamp = pctstamp;
  }


  public String getWordstamp() {
    return wordstamp;
  }

  public void setWordstamp(String wordstamp) {
    this.wordstamp = wordstamp;
  }


  public String getLinkstamp() {
    return linkstamp;
  }

  public void setLinkstamp(String linkstamp) {
    this.linkstamp = linkstamp;
  }


  public String getEntby() {
    return entby;
  }

  public void setEntby(String entby) {
    this.entby = entby;
  }


  public String getLstchgby() {
    return lstchgby;
  }

  public void setLstchgby(String lstchgby) {
    this.lstchgby = lstchgby;
  }


  public java.sql.Timestamp getEntdte() {
    return entdte;
  }

  public void setEntdte(java.sql.Timestamp entdte) {
    this.entdte = entdte;
  }


  public java.sql.Timestamp getLstchgdte() {
    return lstchgdte;
  }

  public void setLstchgdte(java.sql.Timestamp lstchgdte) {
    this.lstchgdte = lstchgdte;
  }


  public String getRcdsts() {
    return rcdsts;
  }

  public void setRcdsts(String rcdsts) {
    this.rcdsts = rcdsts;
  }


  public String getCrtdoctyp() {
    return crtdoctyp;
  }

  public void setCrtdoctyp(String crtdoctyp) {
    this.crtdoctyp = crtdoctyp;
  }


  public String getAgrdoctyp() {
    return agrdoctyp;
  }

  public void setAgrdoctyp(String agrdoctyp) {
    this.agrdoctyp = agrdoctyp;
  }


  public String getAprdoctyp() {
    return aprdoctyp;
  }

  public void setAprdoctyp(String aprdoctyp) {
    this.aprdoctyp = aprdoctyp;
  }


  public String getExedoctyp() {
    return exedoctyp;
  }

  public void setExedoctyp(String exedoctyp) {
    this.exedoctyp = exedoctyp;
  }


  public String getLifetime() {
    return lifetime;
  }

  public void setLifetime(String lifetime) {
    this.lifetime = lifetime;
  }


  public String getSavetime() {
    return savetime;
  }

  public void setSavetime(String savetime) {
    this.savetime = savetime;
  }
}
