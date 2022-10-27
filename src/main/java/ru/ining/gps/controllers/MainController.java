package ru.ining.gps.controllers;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ining.gps.entity.*;
import ru.ining.gps.mappers.CarMapper;
import ru.ining.gps.mappers.DevMapper;
import ru.ining.gps.mssqlmappers.UnionMapper;

import org.zwobble.mammoth.DocumentConverter;
import org.zwobble.mammoth.Result;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import static ru.ining.gps.controllers.lib.AutoGpsLib.*;

@Controller
public class MainController {
    final CarMapper carMapper;
    final DevMapper devMapper;
    final UnionMapper unionMapper;

    public MainController(CarMapper carMapper, DevMapper devMapper, UnionMapper unionMapper) {
        this.carMapper = carMapper; this.devMapper = devMapper; this.unionMapper = unionMapper;
    }

    @Value("${user.mssql.url}")
    private String msSqlUrl;


    @Value("${MASTER_DEP_PERSONS_PSN}")
    public String MASTER_DEP_PERSONS_PSN;

    @Value("${MASTER_PSN}")
    public String MASTER_PSN;

    @Value("${BRN}")
    public int BRN;


    public static class Tcpatmst{
        private int Tcpat;
        private String Des;
//        private String Pctstamp;
//        private String Wordstamp;
//        private String Linkstamp;
        private String Entby;
        private String Lstchgby;
        private Date Entdte;
        private Date Lstchgdte;
        private int Rcdsts;
        private int Crtdoctyp;
        private int Agrdoctyp;
        private int Aprdoctyp;
        private int Exedoctyp;
        private int Lifetime;
        private int Savetime;
        private byte[] Docx;
        private String Comment;
        private int Newtmp;
        private Boolean Multisign;

        public Tcpatmst() {}

        public Tcpatmst(int tcpat, String des, String entby, String lstchgby, Date entdte, Date lstchgdte, int rcdsts, int crtdoctyp, int agrdoctyp, int aprdoctyp, int exedoctyp, int lifetime, int savetime, byte[] docx, String comment, int newtmp, Boolean multisign) {
            Tcpat = tcpat;
            Des = des;
            Entby = entby;
            Lstchgby = lstchgby;
            Entdte = entdte;
            Lstchgdte = lstchgdte;
            Rcdsts = rcdsts;
            Crtdoctyp = crtdoctyp;
            Agrdoctyp = agrdoctyp;
            Aprdoctyp = aprdoctyp;
            Exedoctyp = exedoctyp;
            Lifetime = lifetime;
            Savetime = savetime;
            Docx = docx;
            Comment = comment;
            Newtmp = newtmp;
            Multisign = multisign;
        }

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

        public int getTcpat() {
            return Tcpat;
        }

        public void setTcpat(int tcpat) {
            Tcpat = tcpat;
        }

        public String getDes() {
            return Des;
        }

        public void setDes(String des) {
            Des = des;
        }

        public String getEntby() {
            return Entby;
        }

        public void setEntby(String entby) {
            Entby = entby;
        }

        public String getLstchgby() {
            return Lstchgby;
        }

        public void setLstchgby(String lstchgby) {
            Lstchgby = lstchgby;
        }

        public Date getEntdte() {
            return Entdte;
        }

        public void setEntdte(Date entdte) {
            Entdte = entdte;
        }

        public Date getLstchgdte() {
            return Lstchgdte;
        }

        public void setLstchgdte(Date lstchgdte) {
            Lstchgdte = lstchgdte;
        }

        public int getRcdsts() {
            return Rcdsts;
        }

        public void setRcdsts(int rcdsts) {
            Rcdsts = rcdsts;
        }

        public int getCrtdoctyp() {
            return Crtdoctyp;
        }

        public void setCrtdoctyp(int crtdoctyp) {
            Crtdoctyp = crtdoctyp;
        }

        public int getAgrdoctyp() {
            return Agrdoctyp;
        }

        public void setAgrdoctyp(int agrdoctyp) {
            Agrdoctyp = agrdoctyp;
        }

        public int getAprdoctyp() {
            return Aprdoctyp;
        }

        public void setAprdoctyp(int aprdoctyp) {
            Aprdoctyp = aprdoctyp;
        }

        public int getExedoctyp() {
            return Exedoctyp;
        }

        public void setExedoctyp(int exedoctyp) {
            Exedoctyp = exedoctyp;
        }

        public int getLifetime() {
            return Lifetime;
        }

        public void setLifetime(int lifetime) {
            Lifetime = lifetime;
        }

        public int getSavetime() {
            return Savetime;
        }

        public void setSavetime(int savetime) {
            Savetime = savetime;
        }

        public byte[] getDocx() {
            return Docx;
        }

        public void setDocx(byte[] docx) {
            Docx = docx;
        }
    }

    public static class Tcpatmst_{
        private int Tcpat;
        private String Des;
        //        private String Pctstamp;
//        private String Wordstamp;
//        private String Linkstamp;
        private String Entby;
        private String Lstchgby;
        private Date Entdte;
        private Date Lstchgdte;
        private int Rcdsts;
        private int Crtdoctyp;
        private int Agrdoctyp;
        private int Aprdoctyp;
        private int Exedoctyp;
        private int Lifetime;
        private int Savetime;

        private String CrtdoctypStr;
        private String AgrdoctypStr;
        private String AprdoctypStr;
        private String ExedoctypStr;

        private String onclick;

        public String getOnclick() {
            return "document.location='/edit_tmp?tcpat="+Tcpat+"'";
        }


        public Tcpatmst_() {}

        public int getTcpat() {
            return Tcpat;
        }

        public void setTcpat(int tcpat) {
            Tcpat = tcpat;
        }

        public String getDes() {
            return Des;
        }

        public void setDes(String des) {
            Des = des;
        }

        public String getEntby() {
            return Entby;
        }

        public void setEntby(String entby) {
            Entby = entby;
        }

        public String getLstchgby() {
            return Lstchgby;
        }

        public void setLstchgby(String lstchgby) {
            Lstchgby = lstchgby;
        }

        public Date getEntdte() {
            return Entdte;
        }

        public void setEntdte(Date entdte) {
            Entdte = entdte;
        }

        public Date getLstchgdte() {
            return Lstchgdte;
        }

        public void setLstchgdte(Date lstchgdte) {
            Lstchgdte = lstchgdte;
        }

        public int getRcdsts() {
            return Rcdsts;
        }

        public void setRcdsts(int rcdsts) {
            Rcdsts = rcdsts;
        }

        public int getCrtdoctyp() {
            return Crtdoctyp;
        }

        public void setCrtdoctyp(int crtdoctyp) {
            Crtdoctyp = crtdoctyp;
        }

        public int getAgrdoctyp() {
            return Agrdoctyp;
        }

        public void setAgrdoctyp(int agrdoctyp) {
            Agrdoctyp = agrdoctyp;
        }

        public int getAprdoctyp() {
            return Aprdoctyp;
        }

        public void setAprdoctyp(int aprdoctyp) {
            Aprdoctyp = aprdoctyp;
        }

        public int getExedoctyp() {
            return Exedoctyp;
        }

        public void setExedoctyp(int exedoctyp) {
            Exedoctyp = exedoctyp;
        }

        public int getLifetime() {
            return Lifetime;
        }

        public void setLifetime(int lifetime) {
            Lifetime = lifetime;
        }

        public int getSavetime() {
            return Savetime;
        }

        public void setSavetime(int savetime) {
            Savetime = savetime;
        }


        public String getCrtdoctypStr() {
            return getStrDoctype(Crtdoctyp);
        }

        public String getAgrdoctypStr() {
            return getStrDoctype(Agrdoctyp);
        }

        public String getAprdoctypStr() {
            return getStrDoctype(Aprdoctyp);
        }

        public String getExedoctypStr() {
            return getStrDoctype(Exedoctyp);
        }


        private String getStrDoctype(int doctype) {
            if (doctype == 0)
                return "Нет";
            if (doctype == 1)
                return "Руководитель Предприятия";
            if (doctype == 2)
                return "Руководитель Региона";
            if (doctype == 3)
                return "Руководитель Отдела";
            if (doctype == 4)
                return "Сотрудник";
            if (doctype == 10)
                return "Все";
            if (doctype == 21)
                return "Отдел персонала";
            if (doctype == 22)
                return "Транcпортный отдел";
            if (doctype == 23)
                return "Юридический отдел";
            return "";
        }
    }

    public static class Dep{
        String val;
        int key1;
        int key2;
        String key12;

        public String getKey12() {
            return key1 + "_" + key2;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public int getKey1() {
            return key1;
        }

        public void setKey1(int key1) {
            this.key1 = key1;
        }

        public int getKey2() {
            return key2;
        }

        public void setKey2(int key2) {
            this.key2 = key2;
        }

        public Dep(String val, int key1, int key2) {
            this.val = val;
            this.key1 = key1;
            this.key2 = key2;
        }
    }

    public static class Templeate {
        private String Des;
        private int Tcpat;

        public String getDes() {
            return Des;
        }

        public void setDes(String des) {
            this.Des = des;
        }

        public int getTcpat() {
            return Tcpat;
        }

        public void setTcpat(int tcpat) {
            this.Tcpat = tcpat;
        }

        public Templeate(String des, int tcpat) {
            this.Des = des;
            this.Tcpat = tcpat;
        }
    }

    public static class Names {
        private int key1;
        private String value;

        public int getKey1() {
            return key1;
        }

        public void setKey(int key1) {
            this.key1 = key1;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Names(int key1, String value) {
            this.key1 = key1;
            this.value = value;
        }
    }




    @RequestMapping(value = { "/exe_doc" }, method = RequestMethod.POST)
    public String exeDoc(@RequestParam int tcpoa) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Psnmst p = unionMapper.getPsnmst(getPsnLogin(msSqlUrl));
        Tcpoahdr t = unionMapper.getTcpoahdrElem(tcpoa);
        TcpatmstNF tcpatmstNF = unionMapper.getTcpatmstElem(Integer.parseInt(t.getTcpat()));
//        TcpatmstNF tcpatmst = unionMapper.getTcpatmstElem(tcpatmstNF.getNewtmp());
        int tcpoaNew = addDbDoc(tcpatmstNF.getNewtmp(), sdf1.format(new Date()), t.getPsn());

        updateTcpoahdr(msSqlUrl, tcpoaNew, sdf1.format(t.getStrdte()), sdf1.format(t.getStpdte()), Integer.parseInt(t.getQnt()), t.getControlflg(), t.getControlmsg());

        unionMapper.updateTcpoahdrExedte(new Date(), tcpoaNew, Integer.parseInt(t.getTcpoa()));

        return "redirect:/edit_doc?tcpoa="+tcpoaNew;
    }

    @RequestMapping(value = { "/reject_doc" }, method = RequestMethod.GET)
    @ResponseBody
    public String rejectDoc(@RequestParam int tcpoa, @RequestParam String msg) {
        Psnmst p = unionMapper.getPsnmst(getPsnLogin(msSqlUrl));
        Tcpoahdr t = unionMapper.getTcpoahdrElem(tcpoa);

        if(t.getAgrpsnsign().contains(p.getPsn()) && t.getAgrpos().length() == 0)
            unionMapper.updateTcpoahdrRejectAgr(msg, tcpoa);
        else if(t.getApppsnsign().contains(p.getPsn()) && t.getApppos().length() == 0)
            unionMapper.updateTcpoahdrRejectApp(msg, tcpoa);
        else if(t.getExepsnsign().contains(p.getPsn()) && t.getExepos().length() == 0)
            unionMapper.updateTcpoahdrRejectExe(msg, tcpoa);


            return new Gson().toJson(true);
    }

    @RequestMapping(value = { "/check_pass" }, method = RequestMethod.GET)
    @ResponseBody
    public String checkPass(@RequestParam String pass, @RequestParam String psn) {
        Psnmst psnmst = unionMapper.getPsnmst(psn);
        return new Gson().toJson(new BCryptPasswordEncoder().matches(pass, psnmst.getNewpass()));
    }

    @RequestMapping(value = { "/check_code" }, method = RequestMethod.GET)
    @ResponseBody
    public String checkCode(@RequestParam String tcpoalst, @RequestParam String code) {
        boolean res = true;
        String[] sl = tcpoalst.split(";");
        for (String s : sl) {
            int o = Integer.parseInt(s);
            Tcpoahdr t = unionMapper.getTcpoahdrElem(o);
//            TcpatmstNF tnf = unionMapper.getTcpatmstElem(Integer.parseInt(t.getTcpat()));
            if (!t.getCode().equals(code))
                res = false;
        }

        if (res){
            for (String s : sl) {
                int o = Integer.parseInt(s);
                unionMapper.updateTcpoahdrCode("", o);
            }
        }

        return new Gson().toJson(res);
    }

    public int addSignElem(int tcpoa, String psn) throws IOException {
        Tcpoahdr tcpoahdr = unionMapper.getTcpoahdrElem(tcpoa);
        Psnmst psnmst = unionMapper.getPsnmst(psn);


        TcpatmstNF tcpatmst = unionMapper.getTcpatmstElem(Integer.parseInt(tcpoahdr.getTcpat()));

        String psnCrt = "";
        String psndesCrt = "";
        String psnAgr = "";
        String psndesAgr = "";
        String psnApp = "";
        String psndesApp = "";
        String psnExe = "";
        String psndesExe = "";

        if(tcpoahdr.getCrtpsnsign().length() > 0) {
            psnCrt = tcpoahdr.getCrtpsnsign();
            psndesCrt = tcpoahdr.getCrtpsndessign();
        }

        if(tcpoahdr.getAgrpsnsign().length() > 0) {
            psnAgr = tcpoahdr.getAgrpsnsign();
            psndesAgr = tcpoahdr.getAgrpsndessign();
        }

        if(tcpoahdr.getApppsnsign().length() > 0) {
            psnApp = tcpoahdr.getApppsnsign();
            psndesApp = tcpoahdr.getApppsndessign();
        }

        if(tcpoahdr.getExepsnsign().length() > 0) {
            psnExe = tcpoahdr.getExepsnsign();
            psndesExe = tcpoahdr.getExepsndessign();
        }


        String cscdes = "";
        try {
            cscdes = unionMapper.getCscdes(getPsnLogin(msSqlUrl));
        }catch (Exception e){
            e.printStackTrace();
        }
        if(cscdes == null)
            cscdes = "не найден";

        String cscpsn = "";
        try {
            cscpsn = unionMapper.getCscpsn(getPsnLogin(msSqlUrl));
        }catch (Exception e){
            e.printStackTrace();
        }
        if(cscpsn == null)
            cscpsn = "не найден";


        if(!tcpatmst.getCrtdoctyp().equals("0") && !tcpoahdr.getCrtpsnsign().equals(psn) && tcpoahdr.getCrtpsnsign().length() == 0){
            if(tcpatmst.getCrtdoctyp().equals("1")) {
                psnCrt = MASTER_PSN;
                psndesCrt = unionMapper.getPsndes(MASTER_PSN);
            }else if(tcpatmst.getCrtdoctyp().equals("3")){
                psnCrt = cscpsn;
                psndesCrt = cscdes;
            }else if(tcpatmst.getCrtdoctyp().equals("4")) {
                psnCrt = psn;
                psndesCrt = psnmst.getDes();
            }else if(tcpatmst.getCrtdoctyp().equals("21")){
                psnCrt = MASTER_DEP_PERSONS_PSN;
                psndesCrt = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
            }else
                psnCrt="<NNV>";
        }else if(!tcpatmst.getAgrdoctyp().equals("0") && !tcpoahdr.getAgrpsnsign().equals(psn) && tcpoahdr.getAgrpsnsign().length() == 0){
            if(tcpatmst.getAgrdoctyp().equals("1")) {
                psnAgr = MASTER_PSN;
                psndesAgr = unionMapper.getPsndes(MASTER_PSN);
            }else if(tcpatmst.getAgrdoctyp().equals("3")){
                psnAgr = cscpsn;
                psndesAgr = cscdes;
            }else if(tcpatmst.getAgrdoctyp().equals("4")) {
                psnAgr = psn;
                psndesAgr = psnmst.getDes();
            }else if(tcpatmst.getAgrdoctyp().equals("21")){
                psnAgr = MASTER_DEP_PERSONS_PSN;
                psndesAgr = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
            }else
                psnAgr="<NNV>";
        }else if(!tcpatmst.getAprdoctyp().equals("0") && !tcpoahdr.getApppsnsign().equals(psn) && tcpoahdr.getApppsnsign().length() == 0){
            if(tcpatmst.getAprdoctyp().equals("1")) {
                psnApp = MASTER_PSN;
                psndesApp = unionMapper.getPsndes(MASTER_PSN);
            }else if(tcpatmst.getAprdoctyp().equals("3")){
                psnApp = cscpsn;
                psndesApp = cscdes;
            }else if(tcpatmst.getAprdoctyp().equals("4")) {
                psnApp = psn;
                psndesApp = psnmst.getDes();
            }else if(tcpatmst.getAprdoctyp().equals("21")){
                psnApp = MASTER_DEP_PERSONS_PSN;
                psndesApp = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
            }else
                psnApp="<NNV>";
        }else if(!tcpatmst.getExedoctyp().equals("0") && !tcpoahdr.getExepsnsign().equals(psn) && tcpoahdr.getExepsnsign().length() == 0){
            if(tcpatmst.getExedoctyp().equals("1")) {
                psnExe = MASTER_PSN;
                psndesExe = unionMapper.getPsndes(MASTER_PSN);
            }else if(tcpatmst.getExedoctyp().equals("3")){
                psnExe = cscpsn;
                psndesExe = cscdes;
            }else if(tcpatmst.getExedoctyp().equals("4")) {
                psnExe = psn;
                psndesExe = psnmst.getDes();
            }else if(tcpatmst.getExedoctyp().equals("21")){
                psnExe = MASTER_DEP_PERSONS_PSN;
                psndesExe = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
            }else
                psnExe="<NNV>";
        }

//        if(((tcpoahdr.getCrtpos().length()>0 || tcpoahdr.getApppos().length()>0 || tcpoahdr.getAgrpos().length()>0 || tcpoahdr.getExepos().length()>0) || (tcpoahdr.getCrtpsnsign().equals("<NNV>") && tcpoahdr.getApppsnsign().equals("<NNV>") && tcpoahdr.getAgrpsnsign().equals("<NNV>") && tcpoahdr.getExepsnsign().equals("<NNV>"))) && tcpoahdr.getDocx() == null) {
//            System.out.println("QWERTY");
//        }


        AddDoc ad = unionMapper.getDocElem(tcpoa);
        List<DocxReplace> drl = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        drl.add(new DocxReplace("ХфиоРуководителяХ", cscdes));
        drl.add(new DocxReplace("ХномерДокТаХ", ad.getDep()));
        drl.add(new DocxReplace("ХдатаХ", sdf.format(ad.Actdte)));
        drl.add(new DocxReplace("ХФамилияИмяОтчествоХ", ad.Sname + " " + ad.Fname+ " " + ad.Parname));
        drl.add(new DocxReplace("ХФИОХ", ad.FIO));
        drl.add(new DocxReplace("ХтабНомерХ", ad.Tabnum+""));
        if(!sdf.format(ad.Strdte).contains("01.01.1901"))
            drl.add(new DocxReplace("ХдатасХ", sdf.format(ad.Strdte)));
        if(!sdf.format(ad.Stpdte).contains("01.01.1901"))
            drl.add(new DocxReplace("ХдатаПоХ", sdf.format(ad.Stpdte)));
        if(ad.Qnt>0)
            drl.add(new DocxReplace("ХколичествоХ", ad.Qnt+""));
        drl.add(new DocxReplace("ХпредприятиеХ", ad.Brndes));
        drl.add(new DocxReplace("ХнаименованиеПартнераХ", ad.getPrt()));
        drl.add(new DocxReplace("ХсуммаХ", ad.Amt+""));
        drl.add(new DocxReplace("ХотделХ", ad.Dep));
        drl.add(new DocxReplace("ХСотрудникАдресХ ", ad.Adress));
        drl.add(new DocxReplace("ХСотрудникТелХ ", ad.Tel));
        drl.add(new DocxReplace("ХСотрудникЕмайлХ ", ad.Email));
        drl.add(new DocxReplace("ХПаспортСерНомХ ", ad.Passer+ad.Pasnum));
        drl.add(new DocxReplace("ХПаспортДатаВыдачиХ ", sdf.format(ad.Distrdte)));
        drl.add(new DocxReplace("ХПаспортКемВыданХ ", ad.Distrby));
        drl.add(new DocxReplace("ХДолжностьХ ", ad.Func));


        if(tcpatmst.getCrtdoctyp().equals("0"))
            psnCrt = "<NNV>";

        if(tcpatmst.getAgrdoctyp().equals("0"))
            psnAgr = "<NNV>";

        if(tcpatmst.getAprdoctyp().equals("0"))
            psnApp = "<NNV>";

        if(tcpatmst.getExedoctyp().equals("0"))
            psnExe = "<NNV>";

        int controlflg = 2;
        String controlmsg = "Прошел контроль";

        if(ad.getСontrolflg() == 0) {
            long diffInMillies = Math.abs(ad.getStpdte().getTime() - ad.getStrdte().getTime());
            long diff = diffInMillies/86400000L;
            if ((int) diff+1 != ad.Qnt) {
                controlflg = 1;
                controlmsg = "Количество не соответствует заданным датам";
            }
        }else {
            controlflg = ad.getСontrolflg();
            controlmsg = ad.getСontrolmsg();
        }

        int ok = unionMapper.updateTcpoahdr(psnCrt, psndesCrt, psnAgr, psndesAgr, psnApp, psndesApp, psnExe, psndesExe, controlflg, controlmsg, tcpoa);

        if(tcpoahdr.getCrtpsnsign().equals(psn) && tcpoahdr.getCrtpos().length() == 0) {
            unionMapper.addSignCrt(psn, psnmst.getDes(), psnmst.getNewpass(), new Date(), tcpoa);
            if(tcpoahdr.getDocx() == null || tcpoahdr.getDocx().length() == 0)
                setDocxTcpoahdr(msSqlUrl, tcpoa, updateDocx(new XWPFDocument(new ByteArrayInputStream(getDocx(msSqlUrl,ad.getTcpat()))), drl).doc);
        }else if(tcpoahdr.getAgrpsnsign().equals(psn) && tcpoahdr.getAgrpos().length() == 0) {
            unionMapper.addSignAgr(psn, psnmst.getDes(), psnmst.getNewpass(), new Date(), tcpoa);
            if(tcpoahdr.getDocx() == null || tcpoahdr.getDocx().length() == 0)
                setDocxTcpoahdr(msSqlUrl, tcpoa, updateDocx(new XWPFDocument(new ByteArrayInputStream(getDocx(msSqlUrl,ad.getTcpat()))), drl).doc);
        }else if(tcpoahdr.getApppsnsign().equals(psn) && tcpoahdr.getApppos().length() == 0) {
            unionMapper.addSignApp(psn, psnmst.getDes(), psnmst.getNewpass(), new Date(), tcpoa);
            if(tcpoahdr.getDocx() == null || tcpoahdr.getDocx().length() == 0)
                setDocxTcpoahdr(msSqlUrl, tcpoa, updateDocx(new XWPFDocument(new ByteArrayInputStream(getDocx(msSqlUrl,ad.getTcpat()))), drl).doc);
        }else if(tcpoahdr.getExepsnsign().equals(psn) && tcpoahdr.getExepos().length() == 0) {
            unionMapper.addSignExe(psn, psnmst.getDes(), psnmst.getNewpass(), new Date(), tcpoa);
            if(tcpoahdr.getDocx() == null || tcpoahdr.getDocx().length() == 0)
                setDocxTcpoahdr(msSqlUrl, tcpoa, updateDocx(new XWPFDocument(new ByteArrayInputStream(getDocx(msSqlUrl,ad.getTcpat()))), drl).doc);
        }
        return ok;
    }

    @RequestMapping(value = { "/add_sign" }, method = RequestMethod.POST)
    public String addSign(@RequestParam int tcpoa, @RequestParam String psn) throws IOException {
        boolean ok = addSignElem(tcpoa, psn) > 0;

        if(ok)
            return "redirect:/";
        else
            return "redirect:/?error=%D0%9E%D1%88%D0%B8%D0%B1%D0%BA%D0%B0%20%D0%BF%D1%80%D0%B8%20%D0%B7%D0%B0%D0%BF%D0%B8%D1%81%D0%B8%20%D0%B2%20%D0%B1%D0%B0%D0%B7%D1%83%20%D0%B4%D0%B0%D0%BD%D0%BD%D1%8B%D1%85";

    }

    @RequestMapping(value = { "/add_sign_lst" }, method = RequestMethod.POST)
    public String addSignLst(@RequestParam String tcpoalst) throws IOException {
        int res = 0;
        String psn = getPsnLogin(msSqlUrl);
        String[] sl = tcpoalst.split(";");
        for (String tcpoa:sl) {
            Tcpoahdr tcp = unionMapper.getTcpoahdrElem(Integer.parseInt(tcpoa));
            if((tcp.getCrtpsndessign().length() > 0 && !tcp.getCrtpsndessign().contains("не найден") && tcp.getCrtpos().length()==0 && tcp.getCrtpsnsign().equals(psn)) ||
            (tcp.getAgrpsndessign().length() > 0 && !tcp.getAgrpsndessign().contains("не найден") && tcp.getAgrpos().length()==0 && tcp.getAgrpsnsign().equals(psn)) ||
            (tcp.getApppsndessign().length() > 0 && !tcp.getApppsndessign().contains("не найден") && tcp.getApppos().length()==0 && tcp.getApppsnsign().equals(psn)) ||
            (tcp.getExepsndessign().length() > 0 && !tcp.getExepsndessign().contains("не найден") && tcp.getExepos().length()==0) && tcp.getExepsnsign().equals(psn)) {
                res+=addSignElem(Integer.parseInt(tcpoa), psn);
            }
        }


        if(res>0)
            return "redirect:/";
        else
            return "redirect:/?error=%D0%9E%D1%88%D0%B8%D0%B1%D0%BA%D0%B0%20%D0%BF%D1%80%D0%B8%20%D0%B7%D0%B0%D0%BF%D0%B8%D1%81%D0%B8%20%D0%B2%20%D0%B1%D0%B0%D0%B7%D1%83%20%D0%B4%D0%B0%D0%BD%D0%BD%D1%8B%D1%85";

    }

    class Check{
        int count;
        boolean control;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isControl() {
            return control;
        }

        public void setControl(boolean control) {
            this.control = control;
        }

        public Check(int count, boolean control) {
            this.count = count;
            this.control = control;
        }
    }

    @RequestMapping(value = { "/check_tcpoa" }, method = RequestMethod.GET)
    @ResponseBody
    public String check(@RequestParam String tcpoalst) throws IOException {
        String psn = getPsnLogin(msSqlUrl);
        int count = 0;
        String[] sl = tcpoalst.split(";");
        boolean control = false;
        for (String tcpoa:sl) {
            Tcpoahdr tcp = unionMapper.getTcpoahdrElem(Integer.parseInt(tcpoa));
            if((tcp.getCrtpsndessign().length() > 0 && !tcp.getCrtpsndessign().contains("не найден") && tcp.getCrtpos().length()==0 && tcp.getCrtpsnsign().equals(psn)) ||
            (tcp.getAgrpsndessign().length() > 0 && !tcp.getAgrpsndessign().contains("не найден") && tcp.getAgrpos().length()==0 && tcp.getAgrpsnsign().equals(psn)) ||
            (tcp.getApppsndessign().length() > 0 && !tcp.getApppsndessign().contains("не найден") && tcp.getApppos().length()==0 && tcp.getApppsnsign().equals(psn)) ||
            (tcp.getExepsndessign().length() > 0 && !tcp.getExepsndessign().contains("не найден") && tcp.getExepos().length()==0) && tcp.getExepsnsign().equals(psn)) {
                count++;

                if(tcp.getControlflg() == 2) { }
                else if(!control && tcp.getControlflg() == 1)
                    control = true;
            }
        }

        return new Gson().toJson(new Check(count, control));
    }

    @RequestMapping(value = { "/multi_add_doc" }, method = RequestMethod.GET)
    public String addMultiDoc(Model model) {
        Psnmst p = unionMapper.getPsnmst(getPsnLogin(msSqlUrl));
        model.addAttribute("user", p.getDes());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        if(getPsnLogin(msSqlUrl).equals(MASTER_PSN) || getPsnLogin(msSqlUrl).equals(MASTER_DEP_PERSONS_PSN))
            model.addAttribute("template",unionMapper.getTmpLstMa());
        else
            model.addAttribute("template",unionMapper.getTmpLstMaWO());
        model.addAttribute("date", sdf1.format(new Date()));
        model.addAttribute("deps", unionMapper.getDepLst());
        model.addAttribute("pstmst", unionMapper.getPstmst());

        return "multi_add_doc";
    }

    @RequestMapping(value = { "/multi_add" }, method = RequestMethod.POST)
    public String multiAdd(@RequestParam int tmp, @RequestParam String mode, @RequestParam String date, @RequestParam String wp, @RequestParam String date1, @RequestParam String date2, @RequestParam String qnt) throws ParseException {
        boolean ok = false;
        String psn_ = getPsnLogin(msSqlUrl);
        String psndes_ = unionMapper.getPsndes(psn_);

        List<String> sl;
        if(wp.length() == 0) {
            if (mode.length() > 0)
                sl = unionMapper.getPsnLst(Integer.parseInt(mode.split("_")[0]), Integer.parseInt(mode.split("_")[1]));
            else
                sl = unionMapper.getPsnLstNPR();
        }else{
            if (mode.length() > 0)
                sl = unionMapper.getPsnLstPst(Integer.parseInt(mode.split("_")[0]), Integer.parseInt(mode.split("_")[1]), Integer.parseInt(wp));
            else
                sl = unionMapper.getPsnLstNPRPst(Integer.parseInt(wp));
        }

        for (String psn:sl) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int tcpoa = unionMapper.getMaxTcpoa() + 1;
            String psndes = unionMapper.getPsndes(psn);
            String tab = getTabnum(msSqlUrl, psn);
            TcpatmstNF tcpatmst = unionMapper.getTcpatmstElem(tmp);

            String psnCrt = "";
            String psndesCrt = "";
            String psnAgr = "";
            String psndesAgr = "";
            String psnApp = "";
            String psndesApp = "";
            String psnExe = "";
            String psndesExe = "";


            String cscdes = "";
            try {
                cscdes = unionMapper.getCscdes(getPsnLogin(msSqlUrl));
            }catch (Exception e){
                e.printStackTrace();
            }
            if(cscdes == null)
                cscdes = "не найден";

            String cscpsn = "";
            try {
                cscpsn = unionMapper.getCscpsn(getPsnLogin(msSqlUrl));
            }catch (Exception e){
                e.printStackTrace();
            }
            if(cscpsn == null)
                cscpsn = "не найден";


            if(!tcpatmst.getCrtdoctyp().equals("0")){
                if(tcpatmst.getCrtdoctyp().equals("1")) {
                    psnCrt = MASTER_PSN;
                    psndesCrt = unionMapper.getPsndes(MASTER_PSN);
                }else if(tcpatmst.getCrtdoctyp().equals("3")){
                    psnCrt = cscpsn;
                    psndesCrt = cscdes;
                }else if(tcpatmst.getCrtdoctyp().equals("4")) {
                    psnCrt = psn;
                    psndesCrt = psndes;
                }else if(tcpatmst.getCrtdoctyp().equals("21")){
                    psnCrt = MASTER_DEP_PERSONS_PSN;
                    psndesCrt = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
                }else
                    psnCrt="<NNV>";
            }else if(!tcpatmst.getAgrdoctyp().equals("0")){
                if(tcpatmst.getAgrdoctyp().equals("1")) {
                    psnAgr = MASTER_PSN;
                    psndesAgr = unionMapper.getPsndes(MASTER_PSN);
                }else if(tcpatmst.getAgrdoctyp().equals("3")){
                    psnAgr = cscpsn;
                    psndesAgr = cscdes;
                }else if(tcpatmst.getAgrdoctyp().equals("4")) {
                    psnAgr = psn;
                    psndesAgr = psndes;
                }else if(tcpatmst.getAgrdoctyp().equals("21")){
                    psnAgr = MASTER_DEP_PERSONS_PSN;
                    psndesAgr = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
                }else
                    psnAgr="<NNV>";
            }else if(!tcpatmst.getAprdoctyp().equals("0")){
                if(tcpatmst.getAprdoctyp().equals("1")) {
                    psnApp = MASTER_PSN;
                    psndesApp = unionMapper.getPsndes(MASTER_PSN);
                }else if(tcpatmst.getAprdoctyp().equals("3")){
                    psnApp = cscpsn;
                    psndesApp = cscdes;
                }else if(tcpatmst.getAprdoctyp().equals("4")) {
                    psnApp = psn;
                    psndesApp = psndes;
                }else if(tcpatmst.getAprdoctyp().equals("21")){
                    psnApp = MASTER_DEP_PERSONS_PSN;
                    psndesApp = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
                }else
                    psnApp="<NNV>";
            }else if(!tcpatmst.getExedoctyp().equals("0")){
                if(tcpatmst.getExedoctyp().equals("1")) {
                    psnExe = MASTER_PSN;
                    psndesExe = unionMapper.getPsndes(MASTER_PSN);
                }else if(tcpatmst.getExedoctyp().equals("3")){
                    psnExe = cscpsn;
                    psndesExe = cscdes;
                }else if(tcpatmst.getExedoctyp().equals("4")) {
                    psnExe = psn;
                    psndesExe = psndes;
                }else if(tcpatmst.getExedoctyp().equals("21")){
                    psnExe = MASTER_DEP_PERSONS_PSN;
                    psndesExe = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
                }else
                    psnExe="<NNV>";
            }

            if(psnCrt == null)
                psnCrt = "ошибка";
            if(psndesCrt == null)
                psndesCrt = "ошибка";
            if(psnAgr == null)
                psnAgr = "ошибка";
            if(psndesAgr == null)
                psndesAgr = "ошибка";
            if(psnApp == null)
                psnApp = "ошибка";
            if(psndesApp == null)
                psndesApp = "ошибка";
            if(psnExe == null)
                psnExe = "ошибка";
            if(psndesExe == null)
                psndesExe = "ошибка";

            if(tcpatmst.getCrtdoctyp().equals("0"))
                psnCrt = "<NNV>";

            if(tcpatmst.getAgrdoctyp().equals("0"))
                psnAgr = "<NNV>";

            if(tcpatmst.getAprdoctyp().equals("0"))
                psnApp = "<NNV>";

            if(tcpatmst.getExedoctyp().equals("0"))
                psnExe = "<NNV>";

            ok = unionMapper.insertTcpoahdr_(tcpoa, tmp, sdf.parse(date), new Date(), psn, psndes_, tab, psndes, psnCrt, psndesCrt, psnAgr, psndesAgr, psnApp, psndesApp, psnExe, psndesExe, date1.length()>0?sdf.parse(date1):sdf.parse("1901-01-01"), date2.length()>0?sdf.parse(date2):sdf.parse("1901-01-01"), qnt.length()>0?Integer.parseInt(qnt):0);
        }

        if(ok)
            return "redirect:/";
        else
            return "redirect:/?error=%D0%9E%D1%88%D0%B8%D0%B1%D0%BA%D0%B0%20%D0%BF%D1%80%D0%B8%20%D0%B7%D0%B0%D0%BF%D0%B8%D1%81%D0%B8%20%D0%B2%20%D0%B1%D0%B0%D0%B7%D1%83%20%D0%B4%D0%B0%D0%BD%D0%BD%D1%8B%D1%85";

    }

    @RequestMapping(value = { "/add_doc" }, method = RequestMethod.GET)
    public String addDoc(Model model) {
        Psnmst p = unionMapper.getPsnmst(getPsnLogin(msSqlUrl));
        model.addAttribute("user", p.getDes());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        if(getPsnLogin(msSqlUrl).equals(MASTER_PSN) || getPsnLogin(msSqlUrl).equals(MASTER_DEP_PERSONS_PSN))
            model.addAttribute("template",unionMapper.getTmpLst());
        else
            model.addAttribute("template",unionMapper.getTmpLstWO());
        model.addAttribute("date", sdf1.format(new Date()));
        return "add_doc";
    }

    @RequestMapping(value = { "/edit_doc" }, method = RequestMethod.GET)
    public String editDoc(@RequestParam int tcpoa, Model model) throws Exception {
        Psnmst p = unionMapper.getPsnmst(getPsnLogin(msSqlUrl));
        Psnbrn psnbrn = unionMapper.getPsnbrn(p.getPsn());
        model.addAttribute("user", p.getDes());
        model.addAttribute("sms", psnbrn.getAuthorisation().equals("1"));
        if(getPsnLogin(msSqlUrl).equals(MASTER_PSN) || getPsnLogin(msSqlUrl).equals(MASTER_DEP_PERSONS_PSN))
            model.addAttribute("tmp_",unionMapper.getTmpLst());
        else
            model.addAttribute("tmp_",unionMapper.getTmpLstWO());
        model.addAttribute("doc_src", "/get_replaced_doc?tcpoa=" + tcpoa);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        AddDoc ad = unionMapper.getDocElem(tcpoa);

        String strdte = "";
        if(!sdf.format(ad.Strdte).contains("01.01.1901"))
            strdte = sdf1.format(ad.Strdte);

        String stpdte = "";
        if(!sdf.format(ad.Stpdte).contains("01.01.1901"))
            stpdte = sdf1.format(ad.Stpdte);

        model.addAttribute("tcpoa", ad.Tcpoa+"");
        model.addAttribute("tmp", ad.Template);
        model.addAttribute("entdte", sdf.format(ad.Actdte));


        if(ad.Crtpsnsign.equals(p.getPsn()) && ad.Crtpos.length() == 0)
            model.addAttribute("psn", ad.Crtpsnsign);
        else if(ad.Agrpsnsign.equals(p.getPsn()) && ad.Agrpos.length() == 0)
            model.addAttribute("psn", ad.Agrpsnsign);
        else if(ad.Apppsnsign.equals(p.getPsn()) && ad.Apppos.length() == 0)
            model.addAttribute("psn", ad.Apppsnsign);
        else if(ad.Exepsnsign.equals(p.getPsn()) && ad.Exepos.length() == 0)
            model.addAttribute("psn", ad.Exepsnsign);


        byte[] filearray = getDocx(msSqlUrl, ad.Tcpat);

        boolean strDte = filearray != null && foundInDocx(new XWPFDocument(new ByteArrayInputStream(filearray)), "ХдатасХ");
        boolean stpDte = filearray != null && foundInDocx(new XWPFDocument(new ByteArrayInputStream(filearray)), "ХдатаПоХ");
        boolean clnTeg = filearray != null && foundInDocx(new XWPFDocument(new ByteArrayInputStream(filearray)), "ХколичествоХ");

        if(strDte)
            model.addAttribute("date1", strdte);
        if(stpDte)
            model.addAttribute("date2", stpdte);
        if(clnTeg)
            model.addAttribute("qnt", ad.getQnt()!=0?ad.getQnt():"");

        boolean strDteFlg = !sdf.format(ad.Strdte).contains("01.01.1901") && strDte;
        boolean stpDteFlg = !sdf.format(ad.Stpdte).contains("01.01.1901") && stpDte;
        boolean cntFlg = ad.getQnt() > 0 && clnTeg;

        model.addAttribute("sdteflg", strDte);
        model.addAttribute("edteflg", stpDte);
        model.addAttribute("cntflg", clnTeg);

        model.addAttribute("btnflg", ad.Crtpos.length() == 0);


        String psn = getPsnLogin(msSqlUrl);
        Tcpoahdr tcpoahdr = unionMapper.getTcpoahdrElem(tcpoa);

        String s = tcpoahdr.getRejectmsg();

        model.addAttribute("msgFlg", s.length() > 0);
        model.addAttribute("msg", s);

        System.out.println(tcpoahdr.getControlflg());

        model.addAttribute("controlMsg", tcpoahdr.getControlflg() != 0);
        model.addAttribute("cMsg", tcpoahdr.getControlmsg());

        TcpatmstNF tcpatmst = unionMapper.getTcpatmstElem(Integer.parseInt(tcpoahdr.getTcpat()));
        model.addAttribute("commentExe", tcpoahdr.getExepsndessign().length() > 0 && tcpatmst.getComment().length() > 0);
        model.addAttribute("comment", tcpatmst.getComment());

        boolean canSignNow = false;

        if(tcpoahdr.getCrtpsnsign().equals(psn) && tcpoahdr.getCrtpos().length() == 0) {
            canSignNow = true;
        }

        if(tcpoahdr.getAgrpsnsign().equals(psn) && tcpoahdr.getAgrpos().length() == 0) {
            canSignNow = true;
        }

        if(tcpoahdr.getApppsnsign().equals(psn) && tcpoahdr.getApppos().length() == 0) {
            canSignNow = true;
        }

        if(tcpoahdr.getExepsnsign().equals(psn) && tcpoahdr.getExepos().length() == 0) {
            canSignNow = true;
        }


        model.addAttribute("params", canSignNow && (strDteFlg || !strDte) && (stpDteFlg || !stpDte) && (cntFlg || !clnTeg) && !(tcpoahdr.getExepsndessign().length() > 0 && tcpatmst.getNewtmp() > 0 && tcpoahdr.getExepsnsign().equals(psn)));

        model.addAttribute("create", tcpoahdr.getExepsndessign().length() > 0 && tcpatmst.getNewtmp() > 0 && tcpoahdr.getExepsnsign().equals(psn) && tcpoahdr.getNext() == 0);

        return "edit_doc";
    }

    @RequestMapping(value = { "/edit_tmp" }, method = RequestMethod.GET)
    public String editTmp(@RequestParam int tcpat, Model model, RedirectAttributes redirectAttr) {
        Psnmst p = unionMapper.getPsnmst(getPsnLogin(msSqlUrl));
        model.addAttribute("user", p.getDes());
        Tcpatmst t = getTcpatmst(msSqlUrl, tcpat);

        model.addAttribute("docx", (t.getDocx() != null && t.getDocx().length>0));
        model.addAttribute("tmp_src", "/tmp_pdf_file?tcpat=" + tcpat);
        model.addAttribute("Tcpat", t.getTcpat());
        model.addAttribute("Des", t.getDes());
        model.addAttribute("Crtdoctyp", t.getCrtdoctyp());
        model.addAttribute("Agrdoctyp", t.getAgrdoctyp());
        model.addAttribute("Aprdoctyp", t.getAprdoctyp());
        model.addAttribute("Exedoctyp", t.getExedoctyp());
        model.addAttribute("Lifetime", t.getLifetime());
        model.addAttribute("Savetime", t.getSavetime());
        model.addAttribute("template",unionMapper.getTmpLst());
        model.addAttribute("comment",t.getComment());
        model.addAttribute("newtmp",t.getNewtmp());
        model.addAttribute("multisign",t.getMultisign());
//        redirectAttr.addFlashAttribute("Docx", new MockMultipartFile("test.docx", t.getDocx()));

        return "tmp_edit";
    }

    @RequestMapping(value = { "/add_tmp" }, method = RequestMethod.GET)
    public String addTmp(Model model) {
        Psnmst p = unionMapper.getPsnmst(getPsnLogin(msSqlUrl));
        model.addAttribute("user", p.getDes());
        model.addAttribute("template",unionMapper.getTmpLst());

        return "tmp_add";
    }

    @RequestMapping(value = { "/pdfW" }, method = RequestMethod.GET)
    public String pdf(Model model) throws IOException {

        DocumentConverter converter = new DocumentConverter();
        Result<String> result = converter.convertToHtml(new ByteArrayInputStream(getDocx(msSqlUrl,1)));
        String html = result.getValue(); // The generated HTML
        Set<String> warnings = result.getWarnings();

        System.out.println(html);

        model.addAttribute("abc",html);

        return "doc_view";
    }

    @GetMapping(value = "/pdf")
    public void showPDF(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        InputStream inputStream = new FileInputStream(new File("C:/GPS/1.docx"));
//        InputStream is = new ByteArrayInputStream(docxToPdf(setImgDocx(new XWPFDocument(inputStream), "<Отдел>", "<html>\n" +
//                "<head>" +
//                "<style>" +
//                "div{" +
//                "font-size: 15px;" +
//                "font-family: Arial;" +
//                "</style>" +
//                "</head>" +
//                "<div style=\"border: 1px solid blue;padding: 5px;\">" +
//                "\tДокумент подписан (дата подписания)\n" +
//                "\t<br>\n" +
//                "\tУсиленной Простой Электронной Подписью\n" +
//                "\t<br>\n" +
//                "\tФИО сотрудника\n" +
//                "\t<br>\n" +
//                "\tСертификат - ХЭШ Пароля\n" +
//                "\t<br>\n" +
//                "\tПредприятие\n" +
//                "\t<br>\n" +
//                "\tДействителен с (lstchgdte из Psnmst) по (lstchgdte+15 мес. из Psnmst)\n" +
//                "</div>" +
//                "</html>")).toByteArray());

        int nRead;
        while ((nRead = inputStream.read()) != -1) {
            response.getWriter().write(nRead);
        }
    }

    @RequestMapping(value = { "/tmp_pdf_file" }, method = RequestMethod.GET)
    @ResponseBody
    public void docx(@RequestParam int tcpat, HttpServletResponse response) throws IOException {
//        try {
//            DataInputStream pdf = new DataInputStream(new FileInputStream("C:\\GPS\\1.docx"));
//            wrFile(msSqlUrl, 1, pdf);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        response.setContentType("application/pdf");
        InputStream is = new ByteArrayInputStream(getDocx(msSqlUrl,tcpat));
        XWPFDocument docx = new XWPFDocument(is);

        InputStream inputStream = new ByteArrayInputStream(docxToPdf(docx).toByteArray());
        int nRead;
        while ((nRead = inputStream.read()) != -1) {
            response.getWriter().write(nRead);
        }
    }


    public static class AddDoc{
        private int Tcpat;
        private int Tcpoa;
        private String Dep;
        private String Template;
        private String Sname;
        private String Fname;
        private String Parname;
        private Date Strdte;
        private Date Stpdte;
        private int Qnt;
        private String Prt;
        private Date Entdte;
        private Date Actdte;

        private String Tcpoext;
        private String Ptndes;
        private Double Amt;
        private String Brndes;
        private String Tabnum;


        private String Exesign;
        private String Crtpsnsign;
        private String Crtpsndessign;
        private String Crtpos;
        private String Crtsign;
        private String Agrpsnsign;
        private String Agrpsndessign;
        private String Agrpos;
        private String Agrsign;
        private String Apppsnsign;
        private String Apppsndessign;
        private String Apppos;
        private String Appsign;
        private String Exepsnsign;
        private String Exepsndessign;
        private String Exepos;
        private java.sql.Timestamp Crtdtesign;
        private java.sql.Timestamp Agrdtesign;
        private java.sql.Timestamp Appdtesign;
        private java.sql.Timestamp Exedtesign;
        private String Rejectmsg;
        private int Сontrolflg;
        private String Сontrolmsg;
        private String Func;
        private String Adress;
        private String Tel;
        private String Email;
        private String Passer;
        private String Pasnum;
        private Date Distrdte;
        private String Distrby;
        private String FIO;

        public AddDoc(int tcpat, int tcpoa, String dep, String template, String sname, String fname, String parname, Date strdte, Date stpdte, int qnt, String prt, Date entdte, Date actdte, String tcpoext, String ptndes, Double amt, String brndes, String tabnum, String exesign, String crtpsnsign, String crtpsndessign, String crtpos, String crtsign, String agrpsnsign, String agrpsndessign, String agrpos, String agrsign, String apppsnsign, String apppsndessign, String apppos, String appsign, String exepsnsign, String exepsndessign, String exepos, Timestamp crtdtesign, Timestamp agrdtesign, Timestamp appdtesign, Timestamp exedtesign, String rejectmsg, int сontrolflg, String сontrolmsg, String func, String adress, String tel, String email, String passer, String pasnum, Date distrdte, String distrby, String FIO) {
            Tcpat = tcpat;
            Tcpoa = tcpoa;
            Dep = dep;
            Template = template;
            Sname = sname;
            Fname = fname;
            Parname = parname;
            Strdte = strdte;
            Stpdte = stpdte;
            Qnt = qnt;
            Prt = prt;
            Entdte = entdte;
            Actdte = actdte;
            Tcpoext = tcpoext;
            Ptndes = ptndes;
            Amt = amt;
            Brndes = brndes;
            Tabnum = tabnum;
            Exesign = exesign;
            Crtpsnsign = crtpsnsign;
            Crtpsndessign = crtpsndessign;
            Crtpos = crtpos;
            Crtsign = crtsign;
            Agrpsnsign = agrpsnsign;
            Agrpsndessign = agrpsndessign;
            Agrpos = agrpos;
            Agrsign = agrsign;
            Apppsnsign = apppsnsign;
            Apppsndessign = apppsndessign;
            Apppos = apppos;
            Appsign = appsign;
            Exepsnsign = exepsnsign;
            Exepsndessign = exepsndessign;
            Exepos = exepos;
            Crtdtesign = crtdtesign;
            Agrdtesign = agrdtesign;
            Appdtesign = appdtesign;
            Exedtesign = exedtesign;
            Rejectmsg = rejectmsg;
            Сontrolflg = сontrolflg;
            Сontrolmsg = сontrolmsg;
            Func = func;
            Adress = adress;
            Tel = tel;
            Email = email;
            Passer = passer;
            Pasnum = pasnum;
            Distrdte = distrdte;
            Distrby = distrby;
            this.FIO = FIO;
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

        public int getTcpat() {
            return Tcpat;
        }

        public void setTcpat(int tcpat) {
            Tcpat = tcpat;
        }

        public int getTcpoa() {
            return Tcpoa;
        }

        public void setTcpoa(int tcpoa) {
            Tcpoa = tcpoa;
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

        public String getSname() {
            return Sname;
        }

        public void setSname(String sname) {
            Sname = sname;
        }

        public String getFname() {
            return Fname;
        }

        public void setFname(String fname) {
            Fname = fname;
        }

        public String getParname() {
            return Parname;
        }

        public void setParname(String parname) {
            Parname = parname;
        }

        public Date getStrdte() {
            return Strdte;
        }

        public void setStrdte(Date strdte) {
            Strdte = strdte;
        }

        public Date getStpdte() {
            return Stpdte;
        }

        public void setStpdte(Date stpdte) {
            Stpdte = stpdte;
        }

        public int getQnt() {
            return Qnt;
        }

        public void setQnt(int qnt) {
            Qnt = qnt;
        }

        public String getPrt() {
            return Prt;
        }

        public void setPrt(String prt) {
            Prt = prt;
        }

        public Date getEntdte() {
            return Entdte;
        }

        public void setEntdte(Date entdte) {
            Entdte = entdte;
        }

        public Date getActdte() {
            return Actdte;
        }

        public void setActdte(Date actdte) {
            Actdte = actdte;
        }

        public String getTcpoext() {
            return Tcpoext;
        }

        public void setTcpoext(String tcpoext) {
            Tcpoext = tcpoext;
        }

        public String getPtndes() {
            return Ptndes;
        }

        public void setPtndes(String ptndes) {
            Ptndes = ptndes;
        }

        public Double getAmt() {
            return Amt;
        }

        public void setAmt(Double amt) {
            Amt = amt;
        }

        public String getBrndes() {
            return Brndes;
        }

        public void setBrndes(String brndes) {
            Brndes = brndes;
        }

        public String getTabnum() {
            return Tabnum;
        }

        public void setTabnum(String tabnum) {
            Tabnum = tabnum;
        }

        public String getExesign() {
            return Exesign;
        }

        public void setExesign(String exesign) {
            Exesign = exesign;
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

        public String getCrtpos() {
            return Crtpos;
        }

        public void setCrtpos(String crtpos) {
            Crtpos = crtpos;
        }

        public String getCrtsign() {
            return Crtsign;
        }

        public void setCrtsign(String crtsign) {
            Crtsign = crtsign;
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

        public String getAgrpos() {
            return Agrpos;
        }

        public void setAgrpos(String agrpos) {
            Agrpos = agrpos;
        }

        public String getAgrsign() {
            return Agrsign;
        }

        public void setAgrsign(String agrsign) {
            Agrsign = agrsign;
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

        public String getApppos() {
            return Apppos;
        }

        public void setApppos(String apppos) {
            Apppos = apppos;
        }

        public String getAppsign() {
            return Appsign;
        }

        public void setAppsign(String appsign) {
            Appsign = appsign;
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

        public String getExepos() {
            return Exepos;
        }

        public void setExepos(String exepos) {
            Exepos = exepos;
        }

        public Timestamp getCrtdtesign() {
            return Crtdtesign;
        }

        public void setCrtdtesign(Timestamp crtdtesign) {
            Crtdtesign = crtdtesign;
        }

        public Timestamp getAgrdtesign() {
            return Agrdtesign;
        }

        public void setAgrdtesign(Timestamp agrdtesign) {
            Agrdtesign = agrdtesign;
        }

        public Timestamp getAppdtesign() {
            return Appdtesign;
        }

        public void setAppdtesign(Timestamp appdtesign) {
            Appdtesign = appdtesign;
        }

        public Timestamp getExedtesign() {
            return Exedtesign;
        }

        public void setExedtesign(Timestamp exedtesign) {
            Exedtesign = exedtesign;
        }

        public String getRejectmsg() {
            return Rejectmsg;
        }

        public void setRejectmsg(String rejectmsg) {
            Rejectmsg = rejectmsg;
        }

        public String getFunc() {
            return Func;
        }

        public void setFunc(String func) {
            Func = func;
        }

        public String getAdress() {
            return Adress;
        }

        public void setAdress(String adress) {
            Adress = adress;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String tel) {
            Tel = tel;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getPasser() {
            return Passer;
        }

        public void setPasser(String passer) {
            Passer = passer;
        }

        public String getPasnum() {
            return Pasnum;
        }

        public void setPasnum(String pasnum) {
            Pasnum = pasnum;
        }

        public Date getDistrdte() {
            return Distrdte;
        }

        public void setDistrdte(Date distrdte) {
            Distrdte = distrdte;
        }

        public String getDistrby() {
            return Distrby;
        }

        public void setDistrby(String distrby) {
            Distrby = distrby;
        }

        public String getFIO() {
            return FIO;
        }

        public void setFIO(String FIO) {
            this.FIO = FIO;
        }
    }

    public String getHtmlElemReject(String msg, String psnDes){
        if(msg.length() > 121)
            msg = msg.substring(0, 120);
        return  "<div style=\"color: #CD5C5C; font-size: 20px;border: 2px solid #CD5C5C;padding: 5px;width: 600px;height: 150px\">" +
                "<b>Кем отклонен: " + psnDes +
                "</b><br><b style=\"word-break:break-all;\">Комментарий: " + msg +
                "</b></div><br>";
    }

    public String getHtmlElem(String date, String psnDes, String hash, String sDte, String eDte, String brnDes){
        return  "<div style=\"border: 2px solid #2077bb;padding: 5px;width: 600px;\">" +
                "<b>Документ подписан: " + date +
                "</b><br>" +
                "<b>Усиленной Простой Электронной Подписью" +
                "</b><br>" +
                "<b>Кому выдан: " + psnDes +
                "</b><br>" +
                "<b>Сертификат: <br>" + hash +
                "</b><br>" +
                "<b>Действителен с " + sDte + " по " + eDte +
                "</b><br>" +
                "<b>Предприятие: " + brnDes +
                "</b></div><br>";
    }

    @RequestMapping(value = { "/get_replaced_doc" }, method = RequestMethod.GET)
    @ResponseBody
    public void getDocument(@RequestParam int tcpoa, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        AddDoc ad = unionMapper.getDocElem(tcpoa);

        List<DocxReplace> drl = new ArrayList<>();
        String csc = "";
        try {
            csc = unionMapper.getCscdes(getPsnLogin(msSqlUrl));
        }catch (Exception e){
            e.printStackTrace();
        }
        if(csc == null)
            csc = "";

        drl.add(new DocxReplace("ХфиоРуководителяХ", csc));
        drl.add(new DocxReplace("ХномерДокТаХ", ad.getDep()));
        drl.add(new DocxReplace("ХдатаХ", sdf.format(ad.Actdte)));
        drl.add(new DocxReplace("ХФИОХ", ad.FIO));
        drl.add(new DocxReplace("ХФамилияИмяОтчествоХ", ad.Sname + " " + ad.Fname+ " " + ad.Parname));
        drl.add(new DocxReplace("ХтабНомерХ", ad.Tabnum+""));
        if(!sdf.format(ad.Strdte).contains("01.01.1901"))
            drl.add(new DocxReplace("ХдатасХ", sdf.format(ad.Strdte)));
        if(!sdf.format(ad.Stpdte).contains("01.01.1901"))
            drl.add(new DocxReplace("ХдатаПоХ", sdf.format(ad.Stpdte)));
        if(ad.Qnt>0)
            drl.add(new DocxReplace("ХколичествоХ", ad.Qnt+""));
        drl.add(new DocxReplace("ХпредприятиеХ", ad.Brndes));
        drl.add(new DocxReplace("ХнаименованиеПартнераХ", ad.getPrt()));
        drl.add(new DocxReplace("ХсуммаХ", ad.Amt+""));
        drl.add(new DocxReplace("ХотделХ", ad.Dep));
        drl.add(new DocxReplace("ХСотрудникАдресХ ", ad.Adress));
        drl.add(new DocxReplace("ХСотрудникТелХ ", ad.Tel));
        drl.add(new DocxReplace("ХСотрудникЕмайлХ ", ad.Email));
        drl.add(new DocxReplace("ХПаспортСерНомХ ", ad.Passer+ad.Pasnum));
        drl.add(new DocxReplace("ХПаспортДатаВыдачиХ ", sdf.format(ad.Distrdte)));
        drl.add(new DocxReplace("ХПаспортКемВыданХ ", ad.Distrby));
        drl.add(new DocxReplace("ХДолжностьХ ", ad.Func));

        URD rd = updateDocx(new XWPFDocument(new ByteArrayInputStream(getDocx(msSqlUrl,ad.getTcpat()))), drl);
        XWPFDocument docx = rd.doc;


        String html ="<html>" +
                "<head>" +
                "<style>" +
                "div{" +
                "color: #2077bb;" +
                "font-size: 15px;" +
                "</style>" +
                "</head>";

        Psnmst psnmst;
        if(ad.Crtpsnsign.length()>0 && (!ad.Crtpsnsign.contains("<NNV>")) && (!ad.Crtpsnsign.contains("не найден")))
            psnmst = unionMapper.getPsnmst(ad.Crtpsnsign);
        else
            psnmst = unionMapper.getPsnmst(MASTER_PSN);

        LocalDate ld1 = psnmst.getLstchgdte().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        ld1 = ld1.plusMonths(15);

        int cnt = 0;

        if(ad.Crtpos.length() > 0) {
            html += getHtmlElem(sdf.format(ad.Crtdtesign), ad.Crtpsndessign, ad.Crtpos, sdf.format(psnmst.getLstchgdte()), sdf.format(convertToDateViaInstant(ld1)), ad.Brndes);
            cnt++;
        }else if(ad.Crtpsnsign.contains("<REJECT>") && ad.getCrtpsndessign().length()>0) {
            html += getHtmlElemReject(ad.Rejectmsg, ad.getSname() + " " + ad.getFname() + " " + ad.getParname());
            cnt++;
        }

        if(ad.Agrpos.length() > 0) {
            html += getHtmlElem(sdf.format(ad.Agrdtesign), ad.Agrpsndessign, ad.Agrpos, sdf.format(psnmst.getLstchgdte()), sdf.format(convertToDateViaInstant(ld1)), ad.Brndes);
            cnt++;
        }else if(ad.Agrpsnsign.contains("<REJECT>") && ad.getAgrpsndessign().length()>0) {
            html += getHtmlElemReject(ad.Rejectmsg, ad.getSname() + " " + ad.getFname() + " " + ad.getParname());
            cnt++;
        }

        if(ad.Apppos.length() > 0) {
            html += getHtmlElem(sdf.format(ad.Appdtesign), ad.Apppsndessign, ad.Apppos, sdf.format(psnmst.getLstchgdte()), sdf.format(convertToDateViaInstant(ld1)), ad.Brndes);
            cnt++;
        }else if(ad.Apppsnsign.contains("<REJECT>") && ad.getApppsndessign().length()>0) {
            html += getHtmlElemReject(ad.Rejectmsg, ad.getSname() + " " + ad.getFname() + " " + ad.getParname());
            cnt++;
        }

        if(ad.Exepos.length() > 0) {
            html += getHtmlElem(sdf.format(ad.Exedtesign), ad.Exepsndessign, ad.Exepos, sdf.format(psnmst.getLstchgdte()), sdf.format(convertToDateViaInstant(ld1)), ad.Brndes);
            cnt++;
        }else if(ad.Exepsnsign.contains("<REJECT>") && ad.getExepsndessign().length()>0) {
            html += getHtmlElemReject(ad.Rejectmsg, ad.getSname() + " " + ad.getFname() + " " + ad.getParname());
            cnt++;
        }


        html += "</html>";
        if(cnt>0)
            docx = setImgDocx(docx, "ХЭЦПХ", html, cnt);


        InputStream inputStream = new ByteArrayInputStream(docxToPdf(docx).toByteArray());
        int nRead;
        while ((nRead = inputStream.read()) != -1) {
            response.getWriter().write(nRead);
        }
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }


    @RequestMapping(value = { "/tmp_lst",  }, method = RequestMethod.GET)
    public String tmpLst(Model model) {
        Psnmst p = unionMapper.getPsnmst(getPsnLogin(msSqlUrl));
        model.addAttribute("user", p.getDes());

        if(getPsnLogin(msSqlUrl).equals(MASTER_PSN) || getPsnLogin(msSqlUrl).equals(MASTER_DEP_PERSONS_PSN))
            model.addAttribute("tmpList", getTcpatmstLst(msSqlUrl));
        else
            model.addAttribute("tmpList", getTcpatmstLstWO(msSqlUrl));


        return "tmp_lst";
    }

    @RequestMapping(value = { "/sms_request_lst",  }, method = RequestMethod.GET)
    @ResponseBody
    public String smsRequestLst(@RequestParam String tcpoalst) throws Exception {
        String[] sl = tcpoalst.split(";");
        String code = RandomStringUtils.random(4, false, true);
        String text = "Ваш СМС код для "+sl.length+" - "+code;

        for (String s:sl) {
            unionMapper.updateTcpoahdrCode(code, Integer.parseInt(s));
        }

        Psnmst p = unionMapper.getPsnmst(getPsnLogin(msSqlUrl));

        URL url = new URL("https://gate.smsaero.ru/v2/sms/send?number=79262314800&text="+text+"&sign=SMS Aero");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String auth = "kkorneev328@gmail.com:XtE6Ruawv0wQBiwpFCfqHXu52Eor";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);
        connection.setRequestProperty("Authorization", authHeaderValue);
        connection.setRequestProperty("accept", "application/json");

        InputStream responseStream = connection.getInputStream();
        System.out.println(responseStream.readAllBytes().toString());

        return "";
    }

    @RequestMapping(value = { "/sms_request_one",  }, method = RequestMethod.GET)
    @ResponseBody
    public String smsRequest(@RequestParam int tcpoa) throws Exception {
        String code = RandomStringUtils.random(4, false, true);
        Tcpoahdr t = unionMapper.getTcpoahdrElem(tcpoa);
        String text = "Ваш СМС код для документа №"+tcpoa+" от "+t.getActdte()+" - "+code;

        unionMapper.updateTcpoahdrCode(code, tcpoa);

        URL url = new URL("https://gate.smsaero.ru/v2/sms/send?number=79262314800&text="+text+"&sign=SMS Aero");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String auth = "kkorneev328@gmail.com:XtE6Ruawv0wQBiwpFCfqHXu52Eor";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);
        connection.setRequestProperty("Authorization", authHeaderValue);
        connection.setRequestProperty("accept", "application/json");

        InputStream responseStream = connection.getInputStream();
        System.out.println(responseStream.readAllBytes().toString());

        return "";
    }


    @RequestMapping(value = { "/", "/doc_lst" }, method = RequestMethod.GET)
    public String docLst(Model model) throws Exception {
        Psnmst p = unionMapper.getPsnmst(getPsnLogin(msSqlUrl));
        Psnbrn psnbrn = unionMapper.getPsnbrn(p.getPsn());
        model.addAttribute("user", p.getDes());
        model.addAttribute("sms", psnbrn.getAuthorisation().equals("1"));
        List<Dep> d1 = unionMapper.getDepLst();
        List<Names> n1 = unionMapper.getFIOLst();
        List<Names> n2 = new ArrayList<>();
        model.addAttribute("psn", getPsnLogin(msSqlUrl));

        for (Names n:n1) {
            boolean founded = false;
            for (Names nn:n2) {
                if(n.value.equals(nn.value)) {
                    founded = true;
                    break;
                }
            }
            if(!founded)
                n2.add(n);
        }


//        model.addAttribute("tcpoa", getTcpoaLogin(msSqlUrl));
        model.addAttribute("deps", d1);
        model.addAttribute("FIOs", n2);
        if(getPsnLogin(msSqlUrl).equals(MASTER_PSN) || getPsnLogin(msSqlUrl).equals(MASTER_DEP_PERSONS_PSN))
            model.addAttribute("template",unionMapper.getTmpLst());
        else
            model.addAttribute("template",unionMapper.getTmpLstWO());

        return "doc_lst";
    }

    @RequestMapping(value = { "/add_tmp_post" }, method = RequestMethod.POST)
    public String addTmp(@RequestParam String entby, @RequestParam int tcpa, @RequestParam String des, @RequestParam int crt, @RequestParam int app, @RequestParam int agr, @RequestParam int exe, @RequestParam int lifetime, @RequestParam int savetime, @RequestParam MultipartFile fileupload, @RequestParam String comment, @RequestParam int newtmp, @RequestParam boolean multisign) {
        boolean ok;
        if(fileupload.getOriginalFilename().contains(".docx"))
            ok = addTcpatmst(msSqlUrl, entby, tcpa,des,crt,app,agr,exe,lifetime,savetime,fileupload, comment, newtmp, multisign);
        else
            ok = addTcpatmst(msSqlUrl, entby, tcpa,des,crt,app,agr,exe,lifetime,savetime, comment, newtmp, multisign);

        if(ok)
            return "redirect:/tmp_lst";
        else
            return "redirect:/tmp_lst?error=%D0%9E%D1%88%D0%B8%D0%B1%D0%BA%D0%B0%20%D0%BF%D1%80%D0%B8%20%D0%B7%D0%B0%D0%BF%D0%B8%D1%81%D0%B8%20%D0%B2%20%D0%B1%D0%B0%D0%B7%D1%83%20%D0%B4%D0%B0%D0%BD%D0%BD%D1%8B%D1%85";
    }

    int addDbDoc(int tmp, String date, String psn) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int tcpoa = unionMapper.getMaxTcpoa() + 1;
        String psndes = unionMapper.getPsndes(psn);
        String tab = getTabnum(msSqlUrl, psn);


        TcpatmstNF tcpatmst = unionMapper.getTcpatmstElem(tmp);

        String psnCrt = "";
        String psndesCrt = psndes;
        String psnAgr = "";
        String psndesAgr = "";
        String psnApp = "";
        String psndesApp = "";
        String psnExe = "";
        String psndesExe = "";

        String cscdes = "";
        try {
            cscdes = unionMapper.getCscdes(psn);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(cscdes == null)
            cscdes = "не найден";

        String cscpsn = "";
        try {
            cscpsn = unionMapper.getCscpsn(psn);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(cscpsn == null)
            cscpsn = "не найден";

        if(!tcpatmst.getCrtdoctyp().equals("0")){
            if(tcpatmst.getCrtdoctyp().equals("1")) {
                psnCrt = MASTER_PSN;
                psndesCrt = unionMapper.getPsndes(MASTER_PSN);
            }else if(tcpatmst.getCrtdoctyp().equals("3")){
                psnCrt = cscpsn;
                psndesCrt = cscdes;
            }else if(tcpatmst.getCrtdoctyp().equals("4")) {
                psnCrt = psn;
                psndesCrt = psndes;
            }else if(tcpatmst.getCrtdoctyp().equals("21")){
                psnCrt = MASTER_DEP_PERSONS_PSN;
                psndesCrt = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
            }else
                psnCrt="<NNV>";
        }else if(!tcpatmst.getAgrdoctyp().equals("0")){
            if(tcpatmst.getAgrdoctyp().equals("1")) {
                psnAgr = MASTER_PSN;
                psndesAgr = unionMapper.getPsndes(MASTER_PSN);
            }else if(tcpatmst.getAgrdoctyp().equals("3")){
                psnAgr = cscpsn;
                psndesAgr = cscdes;
            }else if(tcpatmst.getAgrdoctyp().equals("4")) {
                psnAgr = psn;
                psndesAgr = psndes;
            }else if(tcpatmst.getAgrdoctyp().equals("21")){
                psnAgr = MASTER_DEP_PERSONS_PSN;
                psndesAgr = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
            }else
                psnAgr="<NNV>";
        }else if(!tcpatmst.getAprdoctyp().equals("0")){
            if(tcpatmst.getAprdoctyp().equals("1")) {
                psnApp = MASTER_PSN;
                psndesApp = unionMapper.getPsndes(MASTER_PSN);
            }else if(tcpatmst.getAprdoctyp().equals("3")){
                psnApp = cscpsn;
                psndesApp = cscdes;
            }else if(tcpatmst.getAprdoctyp().equals("4")) {
                psnApp = psn;
                psndesApp = psndes;
            }else if(tcpatmst.getAprdoctyp().equals("21")){
                psnApp = MASTER_DEP_PERSONS_PSN;
                psndesApp = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
            }else
                psnApp="<NNV>";
        }else if(!tcpatmst.getExedoctyp().equals("0")){
            if(tcpatmst.getExedoctyp().equals("1")) {
                psnExe = MASTER_PSN;
                psndesExe = unionMapper.getPsndes(MASTER_PSN);
            }else if(tcpatmst.getExedoctyp().equals("3")){
                psnExe = cscpsn;
                psndesExe = cscdes;
            }else if(tcpatmst.getExedoctyp().equals("4")) {
                psnExe = psn;
                psndesExe = psndes;
            }else if(tcpatmst.getExedoctyp().equals("21")){
                psnExe = MASTER_DEP_PERSONS_PSN;
                psndesExe = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
            }else
                psnExe="<NNV>";
        }

        if(psnCrt == null)
            psnCrt = "ошибка";
        if(psndesCrt == null)
            psndesCrt = "ошибка";
        if(psnAgr == null)
            psnAgr = "ошибка";
        if(psndesAgr == null)
            psndesAgr = "ошибка";
        if(psnApp == null)
            psnApp = "ошибка";
        if(psndesApp == null)
            psndesApp = "ошибка";
        if(psnExe == null)
            psnExe = "ошибка";
        if(psndesExe == null)
            psndesExe = "ошибка";

        if(tcpatmst.getCrtdoctyp().equals("0"))
            psnCrt = "<NNV>";

        if(tcpatmst.getAgrdoctyp().equals("0"))
            psnAgr = "<NNV>";

        if(tcpatmst.getAprdoctyp().equals("0"))
            psnApp = "<NNV>";

        if(tcpatmst.getExedoctyp().equals("0"))
            psnExe = "<NNV>";

        unionMapper.insertTcpoahdr(tcpoa, tmp, sdf.parse(date), new Date(), psn, psndes, tab, psndes, psnCrt, psndesCrt, psnAgr, psndesAgr, psnApp, psndesApp, psnExe, psndesExe);


        return tcpoa;
    }

    int addDbDoc(int tmp, String date) throws ParseException {
        String psn = getPsnLogin(msSqlUrl);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int tcpoa = unionMapper.getMaxTcpoa() + 1;
        String psndes = unionMapper.getPsndes(psn);
        String tab = getTabnum(msSqlUrl, psn);


        TcpatmstNF tcpatmst = unionMapper.getTcpatmstElem(tmp);

        String psnCrt = "";
        String psndesCrt = "";
        String psnAgr = "";
        String psndesAgr = "";
        String psnApp = "";
        String psndesApp = "";
        String psnExe = "";
        String psndesExe = "";


        String cscdes = "";
        try {
            cscdes = unionMapper.getCscdes(psn);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(cscdes == null)
            cscdes = "не найден";

        String cscpsn = "";
        try {
            cscpsn = unionMapper.getCscpsn(psn);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(cscpsn == null)
            cscpsn = "не найден";


        if(!tcpatmst.getCrtdoctyp().equals("0")){
            if(tcpatmst.getCrtdoctyp().equals("1")) {
                psnCrt = MASTER_PSN;
                psndesCrt = unionMapper.getPsndes(MASTER_PSN);
            }else if(tcpatmst.getCrtdoctyp().equals("3")){
                psnCrt = cscpsn;
                psndesCrt = cscdes;
            }else if(tcpatmst.getCrtdoctyp().equals("4")) {
                psnCrt = psn;
                psndesCrt = psndes;
            }else if(tcpatmst.getCrtdoctyp().equals("21")){
                psnCrt = MASTER_DEP_PERSONS_PSN;
                psndesCrt = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
            }else
                psnCrt="<NNV>";
        }else if(!tcpatmst.getAgrdoctyp().equals("0")){
            if(tcpatmst.getAgrdoctyp().equals("1")) {
                psnAgr = MASTER_PSN;
                psndesAgr = unionMapper.getPsndes(MASTER_PSN);
            }else if(tcpatmst.getAgrdoctyp().equals("3")){
                psnAgr = cscpsn;
                psndesAgr = cscdes;
            }else if(tcpatmst.getAgrdoctyp().equals("4")) {
                psnAgr = psn;
                psndesAgr = psndes;
            }else if(tcpatmst.getAgrdoctyp().equals("21")){
                psnAgr = MASTER_DEP_PERSONS_PSN;
                psndesAgr = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
            }else
                psnAgr="<NNV>";
        }else if(!tcpatmst.getAprdoctyp().equals("0")){
            if(tcpatmst.getAprdoctyp().equals("1")) {
                psnApp = MASTER_PSN;
                psndesApp = unionMapper.getPsndes(MASTER_PSN);
            }else if(tcpatmst.getAprdoctyp().equals("3")){
                psnApp = cscpsn;
                psndesApp = cscdes;
            }else if(tcpatmst.getAprdoctyp().equals("4")) {
                psnApp = psn;
                psndesApp = psndes;
            }else if(tcpatmst.getAprdoctyp().equals("21")){
                psnApp = MASTER_DEP_PERSONS_PSN;
                psndesApp = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
            }else
                psnApp="<NNV>";
        }else if(!tcpatmst.getExedoctyp().equals("0")){
            if(tcpatmst.getExedoctyp().equals("1")) {
                psnExe = MASTER_PSN;
                psndesExe = unionMapper.getPsndes(MASTER_PSN);
            }else if(tcpatmst.getExedoctyp().equals("3")){
                psnExe = cscpsn;
                psndesExe = cscdes;
            }else if(tcpatmst.getExedoctyp().equals("4")) {
                psnExe = psn;
                psndesExe = psndes;
            }else if(tcpatmst.getExedoctyp().equals("21")){
                psnExe = MASTER_DEP_PERSONS_PSN;
                psndesExe = unionMapper.getPsndes(MASTER_DEP_PERSONS_PSN);
            }else
                psnExe="<NNV>";
        }

        if(psnCrt == null)
            psnCrt = "ошибка";
        if(psndesCrt == null)
            psndesCrt = "ошибка";
        if(psnAgr == null)
            psnAgr = "ошибка";
        if(psndesAgr == null)
            psndesAgr = "ошибка";
        if(psnApp == null)
            psnApp = "ошибка";
        if(psndesApp == null)
            psndesApp = "ошибка";
        if(psnExe == null)
            psnExe = "ошибка";
        if(psndesExe == null)
            psndesExe = "ошибка";

        if(tcpatmst.getCrtdoctyp().equals("0"))
            psnCrt = "<NNV>";

        if(tcpatmst.getAgrdoctyp().equals("0"))
            psnAgr = "<NNV>";

        if(tcpatmst.getAprdoctyp().equals("0"))
            psnApp = "<NNV>";

        if(tcpatmst.getExedoctyp().equals("0"))
            psnExe = "<NNV>";


        unionMapper.insertTcpoahdr(tcpoa, tmp, sdf.parse(date), new Date(), psn, psndes, tab, psndes, psnCrt, psndesCrt, psnAgr, psndesAgr, psnApp, psndesApp, psnExe, psndesExe);


        return tcpoa;
    }

    @RequestMapping(value = { "/add_doc_db" }, method = RequestMethod.POST)
    public String addDocDb(@RequestParam int tmp, @RequestParam String date) throws ParseException {
        return "redirect:/edit_doc?tcpoa="+addDbDoc(tmp, date);
    }


    @RequestMapping(value = { "/edit_doc_db" }, method = RequestMethod.POST)
    public String editDocDb(@RequestParam int tcpoa, @RequestParam String date1, @RequestParam String date2, @RequestParam String qnt) throws ParseException {
        AddDoc ad = unionMapper.getDocElem(tcpoa);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if(date1.length() <= 0)
            date1 = "1901-01-01";
        if(date2.length() <= 0)
            date2 = "1901-01-01";
        if(qnt.length() == 0)
            qnt = "0";


        int controlflg = 2;
        String controlmsg = "Прошел контроль";

        if(date1.contains("1901-01-01") || date2.contains("1901-01-01") || qnt.contains("0")){
            controlflg = ad.getСontrolflg();
            controlmsg = ad.getСontrolmsg();
        }else {
            long diffInMillies = Math.abs(sdf.parse(date2).getTime() - sdf.parse(date1).getTime());
            long diff = diffInMillies/86400000L;
            if ((int) diff+1 != Integer.parseInt(qnt)) {
                controlflg = 1;
                controlmsg = "Количество не соответствует заданным датам";
            }
        }


        try {
            updateTcpoahdr(msSqlUrl, tcpoa, date1, date2, Integer.parseInt(qnt), controlflg, controlmsg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/edit_doc?tcpoa="+tcpoa;
    }

    @RequestMapping(value = { "/edit_tmp_post" }, method = RequestMethod.POST)
    public String editTmp(@RequestParam int tcpa, @RequestParam String des, @RequestParam int crt, @RequestParam int app, @RequestParam int agr, @RequestParam int exe, @RequestParam int lifetime, @RequestParam int savetime, @RequestParam MultipartFile fileupload, @RequestParam String comment, @RequestParam int newtmp, @RequestParam boolean multisign) {
        boolean ok;
        if(fileupload.getOriginalFilename().contains(".docx"))
            ok = editDevice(msSqlUrl,tcpa,des,crt,app,agr,exe,lifetime,savetime,fileupload, comment, newtmp, multisign);
        else
            ok = editDevice(msSqlUrl,tcpa,des,crt,app,agr,exe,lifetime,savetime, comment, newtmp, multisign);

        if(ok)
            return "redirect:/tmp_lst";
        else
            return "redirect:/tmp_lst?error=%D0%9E%D1%88%D0%B8%D0%B1%D0%BA%D0%B0%20%D0%BF%D1%80%D0%B8%20%D0%B7%D0%B0%D0%BF%D0%B8%D1%81%D0%B8%20%D0%B2%20%D0%B1%D0%B0%D0%B7%D1%83%20%D0%B4%D0%B0%D0%BD%D0%BD%D1%8B%D1%85";
    }

    @RequestMapping(value = { "/get_docs_lst" }, method = RequestMethod.GET)
    @ResponseBody
    public String routsGet(@RequestParam int pageMrk, HttpServletRequest request, Model model) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar csd_ = Calendar.getInstance();
        csd_.setTime(new Date());
        csd_.add(Calendar.DATE, -90);
        String SD = sdf.format(csd_.getTime());

        Calendar ced_ = Calendar.getInstance();
        ced_.setTime(new Date());
        ced_.add(Calendar.DATE, 30);
        String ED = sdf.format(ced_.getTime());


        int control = -1;
        String template = "";
        String dep = "";
        String FIO = "";
        String sort = "b.TcpoaDESC";
        String sDate = SD;
        String eDate = ED;
        int pageRowCount = 20;
        boolean onSign = false;

        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            String name = cookie.getName();
            String value = cookie.getValue();
            if(name.equals("tmp")) {
                template = value;
            } else if(name.equals("onSign")) {
                onSign = value.equals("true");
            } else if(name.equals("dep")) {
                dep = value;
            } else if(name.equals("fio")) {
                FIO = value;
            } else if(name.equals("startTime")) {
                sDate = value;
            } else if(name.equals("endTime")) {
                eDate = value;
            } else if(name.equals("pageRowCount")) {
                pageRowCount = Integer.parseInt(value);
            } else if(name.equals("sort")) {
                sort = value;
            } else if(name.equals("control")) {
                control = Integer.parseInt(value);
        }
            System.out.println(cookie.getName() + " : " + cookie.getValue());
        }

        String p = getPsnLogin(msSqlUrl);

        List<DocElem> docElems = getDocElemPage(msSqlUrl, sort.replace("DESC", " DESC"), sdf.parse(sDate), sdf.parse(eDate), pageRowCount*pageMrk, pageRowCount, dep, template, FIO, control, (p.equals(MASTER_PSN) || p.equals(MASTER_DEP_PERSONS_PSN)), p, onSign);

        Rest rest = new Rest(docElems, sort.contains("DESC")?"desc":"asc", sort, pageRowCount, getDocElem(msSqlUrl, sdf.parse(sDate), sdf.parse(eDate), dep, template, FIO, control, (p.equals(MASTER_PSN) || p.equals(MASTER_DEP_PERSONS_PSN)), p, onSign).size(), sDate, eDate, template, FIO, dep, control, onSign);
        return new Gson().toJson(rest);
    }

    class Rest {
        private List<DocElem> lst;
        private String className;
        private String sort;
        private int pageRowCount;
        private int maxPageCount;
        private String sDate;
        private String eDate;
        private String tmp;
        private String fio;
        private String dep;
        private int control;
        private boolean onSign;

        public List<DocElem> getLst() {
            return lst;
        }

        public void setLst(List<DocElem> lst) {
            this.lst = lst;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public int getPageRowCount() {
            return pageRowCount;
        }

        public void setPageRowCount(int pageRowCount) {
            this.pageRowCount = pageRowCount;
        }

        public int getMaxPageCount() {
            return maxPageCount;
        }

        public void setMaxPageCount(int maxPageCount) {
            this.maxPageCount = maxPageCount;
        }

        public String getsDate() {
            return sDate;
        }

        public void setsDate(String sDate) {
            this.sDate = sDate;
        }

        public String geteDate() {
            return eDate;
        }

        public void seteDate(String eDate) {
            this.eDate = eDate;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getFio() {
            return fio;
        }

        public void setFio(String fio) {
            this.fio = fio;
        }

        public String getDep() {
            return dep;
        }

        public void setDep(String dep) {
            this.dep = dep;
        }

        public int getControl() {
            return control;
        }

        public void setControl(int control) {
            this.control = control;
        }

        public boolean isOnSign() {
            return onSign;
        }

        public void setOnSign(boolean onSign) {
            this.onSign = onSign;
        }

        public Rest(List<DocElem> lst, String className, String sort, int pageRowCount, int maxPageCount, String sDate, String eDate, String tmp, String fio, String dep, int control, boolean onSign) {
            this.lst = lst;
            this.className = className;
            this.sort = sort;
            this.pageRowCount = pageRowCount;
            this.maxPageCount = maxPageCount;
            this.sDate = sDate;
            this.eDate = eDate;
            this.tmp = tmp;
            this.fio = fio;
            this.dep = dep;
            this.control = control;
            this.onSign = onSign;
        }
    }

    public class DocExcel {
        private int Tcpoa;              //N
        private String Actdte;          //Дата
        private String Dep;             //Шаблон
        private String Template;        //Отдел


        private String Crtpsndessign;   //Автор
        private String Crtdtesign;

        private String Agrpsndessign;   //Утвержден
        private String Agrdtesign;

        private String Apppsndessign;   //Согласованно
        private String Appdtesign;

        private String Exepsndessign;   //Исполнен
        private String Exedtesign;

        private String Status;          //Отдел

        public DocExcel(int tcpoa, String actdte, String dep, String template, String crtpsndessign, String crtdtesign, String agrpsndessign, String agrdtesign, String apppsndessign, String appdtesign, String exepsndessign, String exedtesign, String crtpos, String agrpos, String apppos, String exepos, String crtpsnsign, String agrpsnsign, String apppsnsign, String exepsnsign) {
            Tcpoa = tcpoa;
            Actdte = actdte;
            Dep = dep;
            Template = template;
            Crtpsndessign = crtpsndessign;
            Crtdtesign = crtdtesign.contains("01.01.1901")?"":crtdtesign;
            Agrpsndessign = agrpsndessign;
            Agrdtesign = agrdtesign.contains("01.01.1901")?"":agrdtesign;
            Apppsndessign = apppsndessign;
            Appdtesign = appdtesign.contains("01.01.1901")?"":appdtesign;
            Exepsndessign = exepsndessign;
            Exedtesign = exedtesign.contains("01.01.1901")?"":exedtesign;

            if(crtpos.contains("<_TEG_>"))
                Status = "Статус";
            else
            if(crtpsnsign.contains("<REJECT>") || agrpsnsign.contains("<REJECT>") || apppsnsign.contains("<REJECT>") || exepsnsign.contains("<REJECT>") || crtpsnsign.contains("не найден") || agrpsnsign.contains("не найден") || apppsnsign.contains("не найден") || exepsnsign.contains("не найден"))
                Status="Отклонен";
            else if((!crtdtesign.contains("01.01.1901") || crtpsnsign.contains("<NNV>")) && (!agrdtesign.contains("01.01.1901") || agrpsnsign.contains("<NNV>")) && (!appdtesign.contains("01.01.1901") || apppsnsign.contains("<NNV>")) && (!exedtesign.contains("01.01.1901") || exepsnsign.contains("<NNV>")))
                Status="Подписан";
            else
                Status="На подписании";
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public int getTcpoa() {
            return Tcpoa;
        }

        public void setTcpoa(int tcpoa) {
            Tcpoa = tcpoa;
        }

        public String getActdte() {
            return Actdte;
        }

        public void setActdte(String actdte) {
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

        public String getCrtdtesign() {
            return Crtdtesign.contains("01.01.1901")?"":Crtdtesign;
        }

        public void setCrtdtesign(String crtdtesign) {
            Crtdtesign = crtdtesign;
        }

        public String getAgrpsndessign() {
            return Agrpsndessign;
        }

        public void setAgrpsndessign(String agrpsndessign) {
            Agrpsndessign = agrpsndessign;
        }

        public String getAgrdtesign() {
            return Agrdtesign.contains("01.01.1901")?"":Agrdtesign;
        }

        public void setAgrdtesign(String agrdtesign) {
            Agrdtesign = agrdtesign;
        }

        public String getApppsndessign() {
            return Apppsndessign;
        }

        public void setApppsndessign(String apppsndessign) {
            Apppsndessign = apppsndessign;
        }

        public String getAppdtesign() {
            return Appdtesign.contains("01.01.1901")?"":Appdtesign;
        }

        public void setAppdtesign(String appdtesign) {
            Appdtesign = appdtesign;
        }

        public String getExepsndessign() {
            return Exepsndessign;
        }

        public void setExepsndessign(String exepsndessign) {
            Exepsndessign = exepsndessign;
        }

        public String getExedtesign() {
            return Exedtesign.contains("01.01.1901")?"":Exedtesign;
        }

        public void setExedtesign(String exedtesign) {
            Exedtesign = exedtesign;
        }
    }

    private void writeBook(DocExcel doc, Row row) {
        Cell cell = row.createCell(0);
        if(doc.Tcpoa != -9988)
            cell.setCellValue(doc.Tcpoa);
        else
            cell.setCellValue("N");

        cell = row.createCell(1);
        cell.setCellValue(doc.Actdte);

        cell = row.createCell(2);
        cell.setCellValue(doc.Template);

        cell = row.createCell(3);
        cell.setCellValue(doc.Dep);

        cell = row.createCell(4);
        cell.setCellValue(doc.Crtpsndessign);

        cell = row.createCell(5);
        cell.setCellValue(doc.Crtdtesign);

        cell = row.createCell(6);
        cell.setCellValue(doc.Agrpsndessign);

        cell = row.createCell(7);
        cell.setCellValue(doc.Agrdtesign);

        cell = row.createCell(8);
        cell.setCellValue(doc.Apppsndessign);

        cell = row.createCell(9);
        cell.setCellValue(doc.Appdtesign);

        cell = row.createCell(10);
        cell.setCellValue(doc.Exepsndessign);

        cell = row.createCell(11);
        cell.setCellValue(doc.Exedtesign);

        cell = row.createCell(12);
        cell.setCellValue(doc.Status);
    }

    public ByteArrayOutputStream writeExcel(List<DocExcel> listBook) throws Exception {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowCount = 0;
        Row row1 = sheet.createRow(0);
        writeBook(new DocExcel(-9988, "Дата", "Отдел", "Шаблон", "Автор", "Подпись", "Согласовано", "Подпись", "Утвержден", "Подпись", "Исполнен", "Подпись", "<_TEG_>", "", "", "", "", "", "", ""), row1);
        for (DocExcel doc : listBook) {
            Row row = sheet.createRow(++rowCount);
            writeBook(doc, row);
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream;
        }
    }

    @RequestMapping(value = { "/xlsx" }, method = RequestMethod.GET)
    @ResponseBody
    public void getExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        String SD = "1970-01-01";
        String ED = "2099-01-01";

        int control = -1;
        String template = "";
        String dep = "";
        String FIO = "";
        String sort = "b.ActdteDESC";
        String sDate = SD;
        String eDate = ED;
        int pageRowCount = 20;
        boolean onSign = false;

            Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            String name = cookie.getName();
            String value = cookie.getValue();
            if(name.equals("tmp")) {
                template = value;
            } else if(name.equals("onSign")) {
                onSign = Boolean.getBoolean(value);
            } else if(name.equals("dep")) {
                dep = value;
            } else if(name.equals("fio")) {
                FIO = value;
            } else if(name.equals("startTime")) {
                sDate = value;
            } else if(name.equals("endTime")) {
                eDate = value;
            } else if(name.equals("control")) {
                control = Integer.parseInt(value);
            }
            System.out.println(cookie.getName() + " : " + cookie.getValue());
        }

        String p = getPsnLogin(msSqlUrl);

        List<DocElem> docElems = getDocElem(msSqlUrl, sdf1.parse(sDate), sdf1.parse(eDate), dep, template, FIO, control, (p.equals(MASTER_PSN) || p.equals(MASTER_DEP_PERSONS_PSN)), p, onSign);
        List<DocExcel> docExcelList = new ArrayList<>();
        for (DocElem de:docElems)
            docExcelList.add(new DocExcel(de.getTcpoa(), sdf.format(de.getActdte()), de.getDep(), de.getTemplate(), de.getCrtpsndessign(), sdf.format(de.getCrtdtesign()), de.getAgrpsndessign(), sdf.format(de.getAgrdtesign()), de.getApppsndessign(), sdf.format(de.getAppdtesign()), de.getExepsndessign(), sdf.format(de.getExedtesign()), de.getCrtpos(), de.getAgrpos(), de.getApppos(), de.getExepos(), de.getCrtpsnsign(), de.getAgrpsnsign(), de.getApppsnsign(), de.getExepsnsign()));



        byte[] excel = writeExcel(docExcelList).toByteArray();
        String fileName = "docList.xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        InputStream inputStream = new ByteArrayInputStream(excel);
        int nRead;
        while ((nRead = inputStream.read()) != -1) {
            response.getWriter().write(nRead);
        }
    }
    @RequestMapping(value = { "/send_mile" }, method = RequestMethod.GET)
    public String sendMile(@RequestParam String psn, @RequestParam int tcpoa) {
        Psnmst p = unionMapper.getPsnmst(psn);
//        Tcpoahdr tcpoahdr = unionMapper.getTcpoahdrElem(tcpoa);

        String[] sl = new String[]{p.getEmail(), "m1607@yandex.ru", "kkorneev328@gmail.com"};
        //String[] sl = new String[]{"kkorneev331@gmail.com", "kkorneev328@gmail.com"};
        try {
            setMailDetailsForSend("Уведомление о подписании документа №"+tcpoa, "<html><b>Вы можете подписать документ №"+tcpoa+".</b><br><b>Для этого перейдите по <a href=\"https://hrdoc.ining.ru/edit_doc?tcpoa="+tcpoa+"\">ссылке</a></b></html>", sl, "noreply@ining.ru");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @Autowired
    private JavaMailSender mailSender;
    private void setMailDetailsForSend(String title, String text, String[] email, String from) throws Exception {
        final MimeMessage mail = mailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(email);
        helper.setFrom(from);
        helper.setSubject(title);
        helper.setText("text/html", text);
        mailSender.send(mail);
    }
}
