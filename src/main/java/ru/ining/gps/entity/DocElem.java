package ru.ining.gps.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DocElem {
    private int Tcpoa;          //N
    private Date Actdte;        //Дата
    private String Dep;         //Шаблон
    private String Template;    //Отдел


    private String Crtpsnsign;  //Автор
    private String Crtpsndessign;  //Автор
    private Date Crtdtesign;

    private String Agrpsnsign;  //Утвержден
    private String Agrpsndessign;  //Утвержден
    private Date Agrdtesign;

    private String Apppsnsign;  //Согласованно
    private String Apppsndessign;  //Согласованно
    private Date Appdtesign;

    private String Exepsnsign;  //Исполнен
    private String Exepsndessign;  //Исполнен
    private Date Exedtesign;

    private int Сontrolflg;
    private String Сontrolmsg;

    private boolean checkbox;



    private String CrtpsndessignStyle;  //Автор
    private String CrtdtesignStyle;
    private String CrtdtesignBtn;
    private boolean CrtdtesignStyleImg;

    private String AgrpsndessignStyle;  //Утвержден
    private String AgrdtesignStyle;
    private String AgrdtesignBtn;
    private boolean AgrdtesignStyleImg;


    private String ApppsndessignStyle;  //Согласованно
    private String AppdtesignStyle;
    private String AppdtesignBtn;
    private boolean AppdtesignStyleImg;

    private String ExepsndessignStyle;  //Исполнен
    private String ExedtesignStyle;
    private String ExedtesignBtn;
    private boolean ExedtesignStyleImg;

    public String getCrtdtesignBtn() {
        return CrtdtesignBtn;
    }

    public void setCrtdtesignBtn(String crtdtesignBtn) {
        CrtdtesignBtn = crtdtesignBtn;
    }

    public String getAgrdtesignBtn() {
        return AgrdtesignBtn;
    }

    public void setAgrdtesignBtn(String agrdtesignBtn) {
        AgrdtesignBtn = agrdtesignBtn;
    }

    public String getAppdtesignBtn() {
        return AppdtesignBtn;
    }

    public void setAppdtesignBtn(String appdtesignBtn) {
        AppdtesignBtn = appdtesignBtn;
    }

    public String getExedtesignBtn() {
        return ExedtesignBtn;
    }

    public void setExedtesignBtn(String exedtesignBtn) {
        ExedtesignBtn = exedtesignBtn;
    }

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

    public String getAgrpsnsign() {
        return Agrpsnsign;
    }

    public void setAgrpsnsign(String agrpsnsign) {
        Agrpsnsign = agrpsnsign;
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

    public String getApppsnsign() {
        return Apppsnsign;
    }

    public void setApppsnsign(String apppsnsign) {
        Apppsnsign = apppsnsign;
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

    public String getExepsnsign() {
        return Exepsnsign;
    }

    public void setExepsnsign(String exepsnsign) {
        Exepsnsign = exepsnsign;
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

    public String getCrtpsndessignStyle() {
        return CrtpsndessignStyle;
    }

    public void setCrtpsndessignStyle(String crtpsndessignStyle) {
        CrtpsndessignStyle = crtpsndessignStyle;
    }

    public String getCrtdtesignStyle() {
        return CrtdtesignStyle;
    }

    public void setCrtdtesignStyle(String crtdtesignStyle) {
        CrtdtesignStyle = crtdtesignStyle;
    }

    public boolean isCrtdtesignStyleImg() {
        return CrtdtesignStyleImg;
    }

    public void setCrtdtesignStyleImg(boolean crtdtesignStyleImg) {
        CrtdtesignStyleImg = crtdtesignStyleImg;
    }

    public String getAgrpsndessignStyle() {
        return AgrpsndessignStyle;
    }

    public void setAgrpsndessignStyle(String agrpsndessignStyle) {
        AgrpsndessignStyle = agrpsndessignStyle;
    }

    public String getAgrdtesignStyle() {
        return AgrdtesignStyle;
    }

    public void setAgrdtesignStyle(String agrdtesignStyle) {
        AgrdtesignStyle = agrdtesignStyle;
    }

    public boolean isAgrdtesignStyleImg() {
        return AgrdtesignStyleImg;
    }

    public void setAgrdtesignStyleImg(boolean agrdtesignStyleImg) {
        AgrdtesignStyleImg = agrdtesignStyleImg;
    }

    public String getApppsndessignStyle() {
        return ApppsndessignStyle;
    }

    public void setApppsndessignStyle(String apppsndessignStyle) {
        ApppsndessignStyle = apppsndessignStyle;
    }

    public String getAppdtesignStyle() {
        return AppdtesignStyle;
    }

    public void setAppdtesignStyle(String appdtesignStyle) {
        AppdtesignStyle = appdtesignStyle;
    }

    public boolean isAppdtesignStyleImg() {
        return AppdtesignStyleImg;
    }

    public void setAppdtesignStyleImg(boolean appdtesignStyleImg) {
        AppdtesignStyleImg = appdtesignStyleImg;
    }

    public String getExepsndessignStyle() {
        return ExepsndessignStyle;
    }

    public void setExepsndessignStyle(String exepsndessignStyle) {
        ExepsndessignStyle = exepsndessignStyle;
    }

    public String getExedtesignStyle() {
        return ExedtesignStyle;
    }

    public void setExedtesignStyle(String exedtesignStyle) {
        ExedtesignStyle = exedtesignStyle;
    }

    public boolean isExedtesignStyleImg() {
        return ExedtesignStyleImg;
    }

    public void setExedtesignStyleImg(boolean exedtesignStyleImg) {
        ExedtesignStyleImg = exedtesignStyleImg;
    }

    public int getСontrolflg() {
        return Сontrolflg;
    }

    public void setСontrolflg(int сontrolflg) {
        Сontrolflg = сontrolflg;
    }

    public String getСontrolmsg() {
        return Сontrolmsg;
    }

    public void setСontrolmsg(String сontrolmsg) {
        Сontrolmsg = сontrolmsg;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public DocElem(int tcpoa, Date actdte, String dep, String template, String crtpsnsign, String crtpsndessign, Date crtdtesign, String agrpsnsign, String agrpsndessign, Date agrdtesign, String apppsnsign, String apppsndessign, Date appdtesign, String exepsnsign, String exepsndessign, Date exedtesign, int controlflg, String controlmsg, boolean checkbox) {
        Tcpoa = tcpoa;
        Actdte = actdte;
        Dep = dep;
        Template = template;
        Crtpsnsign = crtpsnsign;
        Crtpsndessign = crtpsndessign;
        Crtdtesign = crtdtesign;
        Agrpsnsign = agrpsnsign;
        Agrpsndessign = agrpsndessign;
        Agrdtesign = agrdtesign;
        Apppsnsign = apppsnsign;
        Apppsndessign = apppsndessign;
        Appdtesign = appdtesign;
        Exepsnsign = exepsnsign;
        Exepsndessign = exepsndessign;
        Exedtesign = exedtesign;
        Сontrolflg=controlflg;
        Сontrolmsg=controlmsg;
        this.checkbox=checkbox;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        if(sdf.format(Crtdtesign).replace("1901-01-01", "").length() == 0 && !Crtpsnsign.contains("<NNV>") && !Crtpsnsign.contains("<REJECT>") && !Crtpsnsign.contains("не найден") && Crtpsnsign.length() > 0)
            CrtdtesignBtn = "<form action=\"/send_mile\" method=\"GET\" enctype=\"multipart/form-data\" onsubmit=\"return confirm('Вы уверены что хотите уведомить "+Crtpsndessign+" по электронной почте?')\"> " +
                    "<input value=\""+Crtpsnsign+"\" type=\"hidden\" name=\"psn\">" +
                    "<input value=\""+tcpoa+"\" type=\"hidden\" name=\"tcpoa\">" +
                    "<button class=\"btn btn-warning\">Уведомить</button></form>";
        else
            CrtdtesignBtn = "";

        if(sdf.format(Agrdtesign).replace("1901-01-01", "").length() == 0 && !Agrpsnsign.contains("<NNV>") && !Agrpsnsign.contains("<REJECT>") && !Agrpsnsign.contains("не найден") && Agrpsnsign.length() > 0)
            AgrdtesignBtn = "<form action=\"/send_mile\" method=\"GET\" enctype=\"multipart/form-data\" onsubmit=\"return confirm('Вы уверены что хотите уведомить "+Agrpsndessign+" по электронной почте?')\"> " +
                    "<input value=\""+Agrpsnsign+"\" type=\"hidden\" name=\"psn\">" +
                    "<input value=\""+tcpoa+"\" type=\"hidden\" name=\"tcpoa\">" +
                    "<button class=\"btn btn-warning\">Уведомить</button></form>";
        else
            AgrdtesignBtn = "";

        if(sdf.format(Appdtesign).replace("1901-01-01", "").length() == 0 && !Apppsnsign.contains("<NNV>") && !Apppsnsign.contains("<REJECT>") && !Apppsnsign.contains("не найден") && Apppsnsign.length() > 0)
            AppdtesignBtn = "<form action=\"/send_mile\" method=\"GET\" enctype=\"multipart/form-data\" onsubmit=\"return confirm('Вы уверены что хотите уведомить "+Apppsndessign+" по электронной почте?')\"> " +
                    "<input value=\""+Apppsnsign+"\" type=\"hidden\" name=\"psn\">" +
                    "<input value=\""+tcpoa+"\" type=\"hidden\" name=\"tcpoa\">" +
                    "<button class=\"btn btn-warning\">Уведомить</button></form>";
        else
            AppdtesignBtn = "";

        if(sdf.format(Exedtesign).replace("1901-01-01", "").length() == 0 && !Exepsnsign.contains("<NNV>") && !Exepsnsign.contains("<REJECT>") && !Exepsnsign.contains("не найден") && Exepsnsign.length() > 0)
            ExedtesignBtn = "<form action=\"/send_mile\" method=\"GET\" enctype=\"multipart/form-data\" onsubmit=\"return confirm('Вы уверены что хотите уведомить "+Exepsndessign+" по электронной почте?')\"> " +
                    "<input value=\""+Exepsnsign+"\" type=\"hidden\" name=\"psn\">" +
                    "<input value=\""+tcpoa+"\" type=\"hidden\" name=\"tcpoa\">" +
                    "<button class=\"btn btn-warning\">Уведомить</button></form>";
        else
            ExedtesignBtn = "";




        if(Crtpsnsign.contains("не найден")) {
            CrtpsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            CrtdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            AgrpsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            AgrdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            ApppsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            AppdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            ExepsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            ExedtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
        }else if(Crtpsnsign.contains("<NNV>") || sdf.format(Crtdtesign).replace("1901-01-01", "").length()>0) {
            CrtpsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#c3d79b\"";
            CrtdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#c3d79b\"";
        }else{
            CrtpsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#ffff66\"";
            CrtdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#ffff66\"";
        }

        if(Agrpsnsign.contains("<REJECT>")) {
            AgrpsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            AgrdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
        }else if((Agrpsnsign.contains("<NNV>") && sdf.format(Crtdtesign).replace("1901-01-01", "").length() > 0) || sdf.format(Agrdtesign).replace("1901-01-01", "").length()>0) {
            AgrpsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#c3d79b\"";
            AgrdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#c3d79b\"";
        }else{
            AgrpsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#ffff66\"";
            AgrdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#ffff66\"";
        }

        if(Apppsnsign.contains("<REJECT>")) {
            ApppsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            AppdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
        }else if((Apppsnsign.contains("<NNV>") && sdf.format(Agrdtesign).replace("1901-01-01", "").length() > 0) || sdf.format(Appdtesign).replace("1901-01-01", "").length()>0) {
            ApppsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#c3d79b\"";
            AppdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#c3d79b\"";
        }else{
            ApppsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#ffff66\"";
            AppdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#ffff66\"";
        }

        if(Exepsnsign.contains("<REJECT>")) {
            ExepsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            ExedtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
        }else if((Exepsnsign.contains("<NNV>") && sdf.format(Appdtesign).replace("1901-01-01", "").length() > 0) || sdf.format(Exedtesign).replace("1901-01-01", "").length()>0) {
            ExepsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#c3d79b\"";
            ExedtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#c3d79b\"";
        }else{
            ExepsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#ffff66\"";
            ExedtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#ffff66\"";
        }


        if(Agrpsnsign.contains("не найден")) {
            AgrpsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            AgrdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            ApppsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            AppdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            ExepsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            ExedtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
        }

        if(Apppsnsign.contains("не найден")) {
            ApppsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            AppdtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            ExepsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            ExedtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
        }

        if(Exepsnsign.contains("не найден")) {
            ExepsndessignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
            ExedtesignStyle = "style=\"vertical-align: middle; text-align: center;\" bgcolor=\"#CD5C5C\"";
        }


        CrtdtesignStyleImg = sdf.format(Crtdtesign).replace("1901-01-01", "").length()>0;

        AgrdtesignStyleImg = sdf.format(Agrdtesign).replace("1901-01-01", "").length()>0;

        AppdtesignStyleImg = sdf.format(Appdtesign).replace("1901-01-01", "").length()>0;

        ExedtesignStyleImg = sdf.format(Exedtesign).replace("1901-01-01", "").length()>0;
    }
}
