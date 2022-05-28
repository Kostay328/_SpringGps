package ru.ining.gps.controllers;


import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ining.gps.entity.DocElem;
import ru.ining.gps.mappers.CarMapper;
import ru.ining.gps.mappers.DevMapper;
import ru.ining.gps.mssqlmappers.UnionMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
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


    @RequestMapping(value = { "/edit_tmp" }, method = RequestMethod.GET)
    public String editTmp(@RequestParam int tcpat, Model model, RedirectAttributes redirectAttr) {
        Tcpatmst t = getTcpatmst(msSqlUrl, tcpat);

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

    @RequestMapping(value = { "/add_tmp" }, method = RequestMethod.GET)
    public String addTmp(Model model) {
        return "tmp_add";
    }

    @RequestMapping(value = { "/pdfW" }, method = RequestMethod.GET)
    public String pdf(Model model) {
        return "doc_view";
    }

    @GetMapping(value = "/pdf")
    public void showPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        //response.setHeader("Content-Disposition", "attachment; filename=\"demo.pdf\"");
        InputStream inputStream = new FileInputStream(new File("C:/Users/Kostya/IdeaProjects/_SpringGps/src/main/resources/static/pdf/doc.pdf"));
        int nRead;
        while ((nRead = inputStream.read()) != -1) {
            response.getWriter().write(nRead);
        }
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

        model.addAttribute("tcpoa", getTcpoaLogin(msSqlUrl));
        model.addAttribute("deps", d1);
        model.addAttribute("FIOs", n1);
        model.addAttribute("template", t1);

        return "doc_lst";
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



    @RequestMapping(value = { "/get_routs_js" }, method = RequestMethod.GET)
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

    @RequestMapping(value = { "/add_tmp_post" }, method = RequestMethod.POST)
    public String addTmp(@RequestParam String entby, @RequestParam int tcpa, @RequestParam String des, @RequestParam int crt, @RequestParam int app, @RequestParam int agr, @RequestParam int exe, @RequestParam int lifetime, @RequestParam int savetime, @RequestParam MultipartFile fileupload) {
        addDevice(msSqlUrl, entby, tcpa,des,crt,app,agr,exe,lifetime,savetime,fileupload);

        return "redirect:/";
    }

    @RequestMapping(value = { "/edit_tmp_post" }, method = RequestMethod.POST)
    public String editTmp(@RequestParam int tcpa, @RequestParam String des, @RequestParam int crt, @RequestParam int app, @RequestParam int agr, @RequestParam int exe, @RequestParam int lifetime, @RequestParam int savetime, @RequestParam MultipartFile fileupload) {
        editDevice(msSqlUrl,tcpa,des,crt,app,agr,exe,lifetime,savetime,fileupload);

        return "redirect:/";
    }

    @RequestMapping(value = { "/car/sort" }, method = RequestMethod.POST)
    public String carViewingSortInput(HttpServletResponse response, @RequestParam String Sort, @RequestParam int cln) {
        response.addCookie(new Cookie("SortCar", Sort));
        response.addCookie(new Cookie("cln", cln + ""));

        return "redirect:/";
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
