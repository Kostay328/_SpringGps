package ru.ining.gps.controllers;


import com.google.gson.Gson;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ining.gps.entity.DocElem;
import ru.ining.gps.entity.Psnmst;
import ru.ining.gps.entity.Tcpoahdr;
import ru.ining.gps.mappers.CarMapper;
import ru.ining.gps.mappers.DevMapper;
import ru.ining.gps.mssqlmappers.UnionMapper;

import org.zwobble.mammoth.DocumentConverter;
import org.zwobble.mammoth.Result;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.text.SimpleDateFormat;

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

        public Tcpatmst() {}

        public Tcpatmst(int tcpat, String des, String entby, String lstchgby, Date entdte, Date lstchgdte, int rcdsts, int crtdoctyp, int agrdoctyp, int aprdoctyp, int exedoctyp, int lifetime, int savetime, byte[] docx) {
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


    @RequestMapping(value = { "/check_pass" }, method = RequestMethod.GET)
    @ResponseBody
    public String checkPass(@RequestParam String pass, @RequestParam String psn) {
        Psnmst psnmst = unionMapper.getPsnmst(psn);
        return new Gson().toJson(new BCryptPasswordEncoder().matches(pass, psnmst.getNewpass()));
    }

    @RequestMapping(value = { "/add_sign" }, method = RequestMethod.POST)
    public String addSign(@RequestParam int tcpoa, @RequestParam String psn) {
        Psnmst psnmst = unionMapper.getPsnmst(psn);
        unionMapper.addSign(psnmst.getPsn(), psnmst.getDes(), psnmst.getNewpass(), new Date(), tcpoa);

        return "redirect:/";
    }

    @RequestMapping(value = { "/multi_add_doc" }, method = RequestMethod.GET)
    public String addMultiDoc(Model model) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("template",unionMapper.getTmpLst());
        model.addAttribute("date", sdf1.format(new Date()));
        model.addAttribute("deps", unionMapper.getDepLst());

        return "multi_add_doc";
    }

    @RequestMapping(value = { "/multi_add" }, method = RequestMethod.POST)
    public String multiAdd(@RequestParam int tmp, @RequestParam String mode, @RequestParam String date, @RequestParam String wp, @RequestParam String date1, @RequestParam String date2, @RequestParam String qnt) throws ParseException {
        String psn_ = getTcpoaLogin(msSqlUrl);
        String psndes_ = unionMapper.getPsndes(psn_);

        List<String> sl;
        if(mode.length() > 0)
            sl = unionMapper.getPsnLst(Integer.parseInt(mode.split("_")[0]), Integer.parseInt(mode.split("_")[1]));
        else
            sl = unionMapper.getPsnLstNPR();

        for (String psn:sl) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int tcpoa = unionMapper.getMaxTcpoa() + 1;
            String psndes = unionMapper.getPsndes(psn);
            String tab = getTabnum(msSqlUrl, psn);

            unionMapper.insertTcpoahdr_(tcpoa, tmp, sdf.parse(date), new Date(), psn, psndes_, tab, psndes, date1.length()>0?sdf.parse(date1):sdf.parse("1901-01-01"), date2.length()>0?sdf.parse(date2):sdf.parse("1901-01-01"), qnt.length()>0?Integer.parseInt(qnt):0);
        }

        return "redirect:/";
    }

    @RequestMapping(value = { "/add_doc" }, method = RequestMethod.GET)
    public String addDoc(Model model) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List<Templeate> t1 = unionMapper.getTmpLst();
        model.addAttribute("template",t1);
        model.addAttribute("date", sdf1.format(new Date()));
        return "add_doc";
    }

    @RequestMapping(value = { "/edit_doc" }, method = RequestMethod.GET)
    public String editDoc(@RequestParam int tcpoa, Model model) throws Exception {
        List<Templeate> t1 = unionMapper.getTmpLst();
        model.addAttribute("tmp_", t1);
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
        model.addAttribute("psn", getTcpoaLogin(msSqlUrl));


        byte[] filearray = getDocx(msSqlUrl, ad.Tcpat);

        boolean strDte = filearray != null && foundInDocx(new XWPFDocument(new ByteArrayInputStream(filearray)), "<датаС>");
        boolean stpDte = filearray != null && sdf.format(ad.Stpdte).contains("01.01.1901") && foundInDocx(new XWPFDocument(new ByteArrayInputStream(filearray)), "<датаПо>");
        boolean clnTeg = filearray != null && foundInDocx(new XWPFDocument(new ByteArrayInputStream(filearray)), "<количество>");

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


        model.addAttribute("params", (strDteFlg || !strDte) & (stpDteFlg || !stpDte) & (cntFlg || !clnTeg));

        return "edit_doc";
    }

    @RequestMapping(value = { "/edit_tmp" }, method = RequestMethod.GET)
    public String editTmp(@RequestParam int tcpat, Model model, RedirectAttributes redirectAttr) {
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
//        redirectAttr.addFlashAttribute("Docx", new MockMultipartFile("test.docx", t.getDocx()));

        return "tmp_edit";
    }

    @RequestMapping(value = { "/add_tmp" }, method = RequestMethod.GET)
    public String addTmp(Model model) {
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

        public AddDoc(int tcpat, int tcpoa, String dep, String template, String sname, String fname, String parname, Date strdte, Date stpdte, int qnt, String prt, Date entdte, Date actdte, String tcpoext, String ptndes, Double amt, String brndes, String tabnum, String exesign, String crtpsnsign, String crtpsndessign, String crtpos, String crtsign, String agrpsnsign, String agrpsndessign, String agrpos, String agrsign, String apppsnsign, String apppsndessign, String apppos, String appsign, String exepsnsign, String exepsndessign, String exepos, Timestamp crtdtesign, Timestamp agrdtesign, Timestamp appdtesign, Timestamp exedtesign) {
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
    }

    @RequestMapping(value = { "/get_replaced_doc" }, method = RequestMethod.GET)
    @ResponseBody
    public void getDocument(@RequestParam int tcpoa, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        AddDoc ad = unionMapper.getDocElem(tcpoa);

        List<DocxReplace> drl = new ArrayList<>();
        drl.add(new DocxReplace("<номерДокТа>", ad.getDep()));
        drl.add(new DocxReplace("<дата>", sdf.format(ad.Actdte)));
        drl.add(new DocxReplace("<ФИО>",ad.Sname + " " + ad.Fname+ " " + ad.Parname));
        drl.add(new DocxReplace("<ТабНомер>", ad.Tabnum+""));
        if(!sdf.format(ad.Strdte).contains("01.01.1901"))
            drl.add(new DocxReplace("<датаС>", sdf.format(ad.Strdte)));
        if(!sdf.format(ad.Stpdte).contains("01.01.1901"))
            drl.add(new DocxReplace("<датаПо>", sdf.format(ad.Stpdte)));
        if(ad.Qnt>999)
            drl.add(new DocxReplace("<количество>", ad.Qnt+""));
        drl.add(new DocxReplace("<предприятие>", ad.Brndes));
        drl.add(new DocxReplace("<наименованиеПартнера>", ad.getPrt()));
        drl.add(new DocxReplace("<сумма>", ad.Amt+""));
        drl.add(new DocxReplace("<отдел>", ad.Dep));

        URD rd = updateDocx(new XWPFDocument(new ByteArrayInputStream(getDocx(msSqlUrl,ad.getTcpat()))), drl);
        XWPFDocument docx = rd.doc;

        if(ad.Crtpsnsign.length() > 0) {
            Psnmst psnmst = unionMapper.getPsnmst(ad.Crtpsnsign);
            LocalDate ld1 = psnmst.getLstchgdte().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            ld1 = ld1.plusMonths(15);

            String html = "<html>" +
                    "<head>" +
                    "<style>" +
                    "div{" +
                    "color: #2077bb;" +
                    "font-size: 15px;" +
                    "</style>" +
                    "</head>" +
                    "<div style=\"border: 2px solid #2077bb;padding: 5px;width: 600px;\">" +
                    "<b>Документ подписан: " + sdf.format(ad.Crtdtesign) +
                    "</b><br>" +
                    "<b>Усиленной Простой Электронной Подписью" +
                    "</b><br>" +
                    "<b>Кому выдан: " + ad.Crtpsndessign +
                    "</b><br>" +
                    "<b>Сертификат: <br>" + ad.Crtpos +
                    "</b><br>" +
                    "<b>Предприятие: ?" +
                    "</b><br>" +
                    "<b>Действителен с " + sdf.format(psnmst.getLstchgdte()) + " по " + sdf.format(convertToDateViaInstant(ld1)) +
                    "</b></div>" +
                    "</html>";

            docx = setImgDocx(docx, "<количество>", html);
        }

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
        List<Tcpatmst_> tl = getTcpatmstLst(msSqlUrl);

        model.addAttribute("tmpList", tl);

        return "tmp_lst";
    }

    @RequestMapping(value = { "/", "/doc_lst" }, method = RequestMethod.GET)
    public String docLst(Model model) {
//        try {
//            DataInputStream pdf = new DataInputStream(new FileInputStream("D:\\1.docx"));
//            wrFile(msSqlUrl, 1, pdf);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        byte[] docx = getDocx(msSqlUrl,1);
//
//        try {
//            FileOutputStream fos = new FileOutputStream("D:\\2.docx");
//            fos.write(docx);
//            fos.flush();
//            fos.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        List<Templeate> t1 = unionMapper.getTmpLst();
        List<Dep> d1 = unionMapper.getDepLst();
        List<Names> n1 = unionMapper.getFIOLst();

//        model.addAttribute("tcpoa", getTcpoaLogin(msSqlUrl));
        model.addAttribute("deps", d1);
        model.addAttribute("FIOs", n1);
        model.addAttribute("template", t1);

        return "doc_lst";
    }

    @RequestMapping(value = { "/add_tmp_post" }, method = RequestMethod.POST)
    public String addTmp(@RequestParam String entby, @RequestParam int tcpa, @RequestParam String des, @RequestParam int crt, @RequestParam int app, @RequestParam int agr, @RequestParam int exe, @RequestParam int lifetime, @RequestParam int savetime, @RequestParam MultipartFile fileupload) {
        if(fileupload.getOriginalFilename().contains(".docx"))
            addTcpatmst(msSqlUrl, entby, tcpa,des,crt,app,agr,exe,lifetime,savetime,fileupload);
        else
            addTcpatmst(msSqlUrl, entby, tcpa,des,crt,app,agr,exe,lifetime,savetime,fileupload);

        return "redirect:/tmp_lst";
    }

    @RequestMapping(value = { "/add_doc_db" }, method = RequestMethod.POST)
    public String addDocDb(@RequestParam int tmp, @RequestParam String date) throws ParseException {
        String psn = getTcpoaLogin(msSqlUrl);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int tcpoa = unionMapper.getMaxTcpoa() + 1;
        String psndes = unionMapper.getPsndes(psn);
        String tab = getTabnum(msSqlUrl, psn);

        unionMapper.insertTcpoahdr(tcpoa, tmp, sdf.parse(date), new Date(), psn, psndes, tab, psndes);


        return "redirect:/edit_doc?tcpoa="+tcpoa;
    }


    @RequestMapping(value = { "/edit_doc_db" }, method = RequestMethod.POST)
    public String editDocDb(@RequestParam int tcpoa, @RequestParam String date1, @RequestParam String date2, @RequestParam String qnt) {
        AddDoc ad = unionMapper.getDocElem(tcpoa);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if(date1.length() <= 0)
            date1 = "1901-01-01";
        if(date2.length() <= 0)
            date2 = "1901-01-01";
        if(qnt.length() == 0)
            qnt = "0";

        try {
            updateTcpoahdr(msSqlUrl, tcpoa, date1, date2, Integer.parseInt(qnt));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/edit_doc?tcpoa="+tcpoa;
    }

    @RequestMapping(value = { "/edit_tmp_post" }, method = RequestMethod.POST)
    public String editTmp(@RequestParam int tcpa, @RequestParam String des, @RequestParam int crt, @RequestParam int app, @RequestParam int agr, @RequestParam int exe, @RequestParam int lifetime, @RequestParam int savetime, @RequestParam MultipartFile fileupload) {
        if(fileupload.getOriginalFilename().contains(".docx"))
            editDevice(msSqlUrl,tcpa,des,crt,app,agr,exe,lifetime,savetime,fileupload);
        else
            editDevice(msSqlUrl,tcpa,des,crt,app,agr,exe,lifetime,savetime);

        return "redirect:/tmp_lst";
    }

    @RequestMapping(value = { "/get_docs_lst" }, method = RequestMethod.GET)
    @ResponseBody
    public String routsGet(@RequestParam int pageMrk, HttpServletRequest request, Model model) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar csd_ = Calendar.getInstance();
//        csd_.setTime(new Date());
//        csd_.add(Calendar.DATE, -90);
//        String SD = sdf.format(csd_.getTime());
//        Calendar ced_ = Calendar.getInstance();
//        ced_.setTime(new Date());
//        ced_.add(Calendar.DATE, 0);
//        String ED = sdf.format(ced_.getTime());

        String SD = "1970-01-01";
        String ED = "2099-01-01";

        String template = "";
        String dep = "";
        String FIO = "";
        String sort = "b.Tcpoa";
        String sDate = SD;
        String eDate = ED;
        int pageRowCount = 20;

        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            String name = cookie.getName();
            String value = cookie.getValue();
            if(name.equals("tmp")) {
                template = value;
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
            }
            System.out.println(cookie.getName() + " : " + cookie.getValue());
        }


//        List<DocElem> docElems = unionMapper.getDocsLst(sort.replace("DESC", " DESC"), sdf.parse(sDate), sdf.parse(eDate), pageRowCount*pageMrk, pageRowCount);
        List<DocElem> docElems = getDocElem(msSqlUrl, sort.replace("DESC", " DESC"), sdf.parse(sDate), sdf.parse(eDate), pageRowCount*pageMrk, pageRowCount, dep, template, FIO);
        Rest rest = new Rest(docElems, sort.contains("DESC")?"desc":"asc", sort, pageRowCount, unionMapper.getDocsCnt(sdf.parse(sDate), sdf.parse(eDate)), sDate, eDate, template, FIO, dep);
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

        public Rest(List<DocElem> lst, String className, String sort, int pageRowCount, int maxPageCount, String sDate, String eDate, String tmp, String fio, String dep) {
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
        }
    }
}
