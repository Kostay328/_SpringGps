package ru.ining.gps.entity;


import java.sql.Timestamp;

public class Tcpoahdr {

  private String tcpoa;
  private String tcpoaext;
  private String tcpoabrn;
  private java.sql.Timestamp actdte;
  private java.sql.Timestamp stpdte;
  private String brn;
  private String vclstamp;
  private String vcl;
  private String regnum;
  private String ter;
  private String psn;
  private String tabnum;
  private String psndes;
  private String tcpat;
  private String ptn;
  private String amt;
  private String taxrte;
  private String header;
  private String text;
  private String pctstamp;
  private String prtflg;
  private String acqflg;
  private java.sql.Timestamp acqdte;
  private String urlid;
  private String entby;
  private String lstchgby;
  private java.sql.Timestamp entdte;
  private java.sql.Timestamp lstchgdte;
  private java.sql.Timestamp strdte;
  private String exesign;
  private String crtpsnsign;
  private String crtpsndessign;
  private String crtpos;
  private String crtsign;
  private String agrpsnsign;
  private String agrpsndessign;
  private String agrpos;
  private String agrsign;
  private String apppsnsign;
  private String apppsndessign;
  private String apppos;
  private String appsign;
  private String exepsnsign;
  private String exepsndessign;
  private String exepos;
  private java.sql.Timestamp crtdtesign;
  private java.sql.Timestamp agrdtesign;
  private java.sql.Timestamp appdtesign;
  private java.sql.Timestamp exedtesign;
  private String qnt;
  private String docx;
  private String rejectmsg;
  private int controlflg;
  private String controlmsg;
  private String code;


  public Tcpoahdr(String tcpoa, String tcpoaext, String tcpoabrn, Timestamp actdte, Timestamp stpdte, String brn, String vclstamp, String vcl, String regnum, String ter, String psn, String tabnum, String psndes, String tcpat, String ptn, String amt, String taxrte, String header, String text, String pctstamp, String prtflg, String acqflg, Timestamp acqdte, String urlid, String entby, String lstchgby, Timestamp entdte, Timestamp lstchgdte, Timestamp strdte, String exesign, String crtpsnsign, String crtpsndessign, String crtpos, String crtsign, String agrpsnsign, String agrpsndessign, String agrpos, String agrsign, String apppsnsign, String apppsndessign, String apppos, String appsign, String exepsnsign, String exepsndessign, String exepos, Timestamp crtdtesign, Timestamp agrdtesign, Timestamp appdtesign, Timestamp exedtesign, String qnt, String docx, String rejectmsg, int controlflg, String controlmsg, String code) {
    this.tcpoa = tcpoa;
    this.tcpoaext = tcpoaext;
    this.tcpoabrn = tcpoabrn;
    this.actdte = actdte;
    this.stpdte = stpdte;
    this.brn = brn;
    this.vclstamp = vclstamp;
    this.vcl = vcl;
    this.regnum = regnum;
    this.ter = ter;
    this.psn = psn;
    this.tabnum = tabnum;
    this.psndes = psndes;
    this.tcpat = tcpat;
    this.ptn = ptn;
    this.amt = amt;
    this.taxrte = taxrte;
    this.header = header;
    this.text = text;
    this.pctstamp = pctstamp;
    this.prtflg = prtflg;
    this.acqflg = acqflg;
    this.acqdte = acqdte;
    this.urlid = urlid;
    this.entby = entby;
    this.lstchgby = lstchgby;
    this.entdte = entdte;
    this.lstchgdte = lstchgdte;
    this.strdte = strdte;
    this.exesign = exesign;
    this.crtpsnsign = crtpsnsign;
    this.crtpsndessign = crtpsndessign;
    this.crtpos = crtpos;
    this.crtsign = crtsign;
    this.agrpsnsign = agrpsnsign;
    this.agrpsndessign = agrpsndessign;
    this.agrpos = agrpos;
    this.agrsign = agrsign;
    this.apppsnsign = apppsnsign;
    this.apppsndessign = apppsndessign;
    this.apppos = apppos;
    this.appsign = appsign;
    this.exepsnsign = exepsnsign;
    this.exepsndessign = exepsndessign;
    this.exepos = exepos;
    this.crtdtesign = crtdtesign;
    this.agrdtesign = agrdtesign;
    this.appdtesign = appdtesign;
    this.exedtesign = exedtesign;
    this.qnt = qnt;
    this.docx = docx;
    this.rejectmsg = rejectmsg;
    this.controlflg = controlflg;
    this.controlmsg = controlmsg;
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTcpoa() {
    return tcpoa;
  }

  public void setTcpoa(String tcpoa) {
    this.tcpoa = tcpoa;
  }


  public String getTcpoaext() {
    return tcpoaext;
  }

  public void setTcpoaext(String tcpoaext) {
    this.tcpoaext = tcpoaext;
  }


  public String getTcpoabrn() {
    return tcpoabrn;
  }

  public void setTcpoabrn(String tcpoabrn) {
    this.tcpoabrn = tcpoabrn;
  }


  public java.sql.Timestamp getActdte() {
    return actdte;
  }

  public void setActdte(java.sql.Timestamp actdte) {
    this.actdte = actdte;
  }


  public java.sql.Timestamp getStpdte() {
    return stpdte;
  }

  public void setStpdte(java.sql.Timestamp stpdte) {
    this.stpdte = stpdte;
  }


  public String getBrn() {
    return brn;
  }

  public void setBrn(String brn) {
    this.brn = brn;
  }


  public String getVclstamp() {
    return vclstamp;
  }

  public void setVclstamp(String vclstamp) {
    this.vclstamp = vclstamp;
  }


  public String getVcl() {
    return vcl;
  }

  public void setVcl(String vcl) {
    this.vcl = vcl;
  }


  public String getRegnum() {
    return regnum;
  }

  public void setRegnum(String regnum) {
    this.regnum = regnum;
  }


  public String getTer() {
    return ter;
  }

  public void setTer(String ter) {
    this.ter = ter;
  }


  public String getPsn() {
    return psn;
  }

  public void setPsn(String psn) {
    this.psn = psn;
  }


  public String getTabnum() {
    return tabnum;
  }

  public void setTabnum(String tabnum) {
    this.tabnum = tabnum;
  }


  public String getPsndes() {
    return psndes;
  }

  public void setPsndes(String psndes) {
    this.psndes = psndes;
  }


  public String getTcpat() {
    return tcpat;
  }

  public void setTcpat(String tcpat) {
    this.tcpat = tcpat;
  }


  public String getPtn() {
    return ptn;
  }

  public void setPtn(String ptn) {
    this.ptn = ptn;
  }


  public String getAmt() {
    return amt;
  }

  public void setAmt(String amt) {
    this.amt = amt;
  }


  public String getTaxrte() {
    return taxrte;
  }

  public void setTaxrte(String taxrte) {
    this.taxrte = taxrte;
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


  public String getPrtflg() {
    return prtflg;
  }

  public void setPrtflg(String prtflg) {
    this.prtflg = prtflg;
  }


  public String getAcqflg() {
    return acqflg;
  }

  public void setAcqflg(String acqflg) {
    this.acqflg = acqflg;
  }


  public java.sql.Timestamp getAcqdte() {
    return acqdte;
  }

  public void setAcqdte(java.sql.Timestamp acqdte) {
    this.acqdte = acqdte;
  }


  public String getUrlid() {
    return urlid;
  }

  public void setUrlid(String urlid) {
    this.urlid = urlid;
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


  public java.sql.Timestamp getStrdte() {
    return strdte;
  }

  public void setStrdte(java.sql.Timestamp strdte) {
    this.strdte = strdte;
  }


  public String getExesign() {
    return exesign;
  }

  public void setExesign(String exesign) {
    this.exesign = exesign;
  }


  public String getCrtpsnsign() {
    return crtpsnsign;
  }

  public void setCrtpsnsign(String crtpsnsign) {
    this.crtpsnsign = crtpsnsign;
  }


  public String getCrtpsndessign() {
    return crtpsndessign;
  }

  public void setCrtpsndessign(String crtpsndessign) {
    this.crtpsndessign = crtpsndessign;
  }


  public String getCrtpos() {
    return crtpos;
  }

  public void setCrtpos(String crtpos) {
    this.crtpos = crtpos;
  }


  public String getCrtsign() {
    return crtsign;
  }

  public void setCrtsign(String crtsign) {
    this.crtsign = crtsign;
  }


  public String getAgrpsnsign() {
    return agrpsnsign;
  }

  public void setAgrpsnsign(String agrpsnsign) {
    this.agrpsnsign = agrpsnsign;
  }


  public String getAgrpsndessign() {
    return agrpsndessign;
  }

  public void setAgrpsndessign(String agrpsndessign) {
    this.agrpsndessign = agrpsndessign;
  }


  public String getAgrpos() {
    return agrpos;
  }

  public void setAgrpos(String agrpos) {
    this.agrpos = agrpos;
  }


  public String getAgrsign() {
    return agrsign;
  }

  public void setAgrsign(String agrsign) {
    this.agrsign = agrsign;
  }


  public String getApppsnsign() {
    return apppsnsign;
  }

  public void setApppsnsign(String apppsnsign) {
    this.apppsnsign = apppsnsign;
  }


  public String getApppsndessign() {
    return apppsndessign;
  }

  public void setApppsndessign(String apppsndessign) {
    this.apppsndessign = apppsndessign;
  }


  public String getApppos() {
    return apppos;
  }

  public void setApppos(String apppos) {
    this.apppos = apppos;
  }


  public String getAppsign() {
    return appsign;
  }

  public void setAppsign(String appsign) {
    this.appsign = appsign;
  }


  public String getExepsnsign() {
    return exepsnsign;
  }

  public void setExepsnsign(String exepsnsign) {
    this.exepsnsign = exepsnsign;
  }


  public String getExepsndessign() {
    return exepsndessign;
  }

  public void setExepsndessign(String exepsndessign) {
    this.exepsndessign = exepsndessign;
  }


  public String getExepos() {
    return exepos;
  }

  public void setExepos(String exepos) {
    this.exepos = exepos;
  }


  public java.sql.Timestamp getCrtdtesign() {
    return crtdtesign;
  }

  public void setCrtdtesign(java.sql.Timestamp crtdtesign) {
    this.crtdtesign = crtdtesign;
  }


  public java.sql.Timestamp getAgrdtesign() {
    return agrdtesign;
  }

  public void setAgrdtesign(java.sql.Timestamp agrdtesign) {
    this.agrdtesign = agrdtesign;
  }


  public java.sql.Timestamp getAppdtesign() {
    return appdtesign;
  }

  public void setAppdtesign(java.sql.Timestamp appdtesign) {
    this.appdtesign = appdtesign;
  }


  public java.sql.Timestamp getExedtesign() {
    return exedtesign;
  }

  public void setExedtesign(java.sql.Timestamp exedtesign) {
    this.exedtesign = exedtesign;
  }


  public String getQnt() {
    return qnt;
  }

  public void setQnt(String qnt) {
    this.qnt = qnt;
  }


  public String getDocx() {
    return docx;
  }

  public void setDocx(String docx) {
    this.docx = docx;
  }


  public String getRejectmsg() {
    return rejectmsg;
  }

  public void setRejectmsg(String rejectmsg) {
    this.rejectmsg = rejectmsg;
  }


  public int getControlflg() {
    return controlflg;
  }

  public void setControlflg(int Controlflg) {
    this.controlflg = Controlflg;
  }


  public String getControlmsg() {
    return controlmsg;
  }

  public void setControlmsg(String Controlmsg) {
    this.controlmsg = Controlmsg;
  }

}
