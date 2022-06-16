package ru.ining.gps.controllers.lib;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
//from  w  w w.  java  2  s . c o m
import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.ining.gps.controllers.MainController;
import ru.ining.gps.entity.*;
import ru.ining.gps.mappers.CarMapper;
import ru.ining.gps.mappers.DevMapper;
import ru.ining.gps.mssqlmappers.UnionMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutoGpsLib {

    private static DecimalFormat df1 = new DecimalFormat("#.#");

//CURRENT DATE
    private static class CDate {
        private String fdate;
        private String ldate;

        public String getFdate() {
            return fdate;
        }

        public String getLdate() {
            return ldate;
        }

        public CDate(String fdate, String ldate) {
            this.fdate = fdate;
            this.ldate = ldate;
        }
    }

    public static String getSign(String psn, Date date, UnionMapper unionMapper){
        Psnmst psnmst = unionMapper.getPsnmst(psn);


        return String.format("Документ подписан (дата подписания)\n" +
                "Усиленной Простой Электронной Подписью\n" +
                "ФИО сотрудника\n" +
                "Сертификат - ХЭШ Пароля\n" +
                "Предприятие\n" +
                "Действителен с (lstchgdte из Psnmst) по (lstchgdte+15 мес. из Psnmst)\n", "", "", "");
    }

    public static ByteArrayOutputStream docxToPdf(XWPFDocument document){
        try {
            PdfOptions options = PdfOptions.create();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(document, out, options);
            return out;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static void wrFile(String connString, int tcpat, DataInputStream dis) throws Exception {
        byte[] pdfData = dis.readAllBytes();
//        dis.readFully(pdfData);  // read from file into byte[] array
        dis.close();

        Connection dbConnection = DriverManager.getConnection(connString);
        PreparedStatement ps = dbConnection.prepareStatement(
                "UPDATE Tcpatmst " +
                        "SET Docx = ? " +
                        "WHERE Tcpat = " + tcpat);
        ps.setBytes(1, pdfData);
        ps.executeUpdate();
    }

    public static void updateTcpoahdr(String connString, int tcpoa, String sdte, String edte, int qnt, int controlflg, String controlmsg) throws Exception {
        Connection dbConnection = DriverManager.getConnection(connString);
        PreparedStatement ps = dbConnection.prepareStatement("UPDATE Tcpoahdr SET Strdte = ?, Stpdte = ?, Qnt = ?, Сontrolflg = ?, Сontrolmsg = ? WHERE Tcpoa = " + tcpoa);

        ps.setDate(1, (java.sql.Date.valueOf(sdte)));
        ps.setDate(2, (java.sql.Date.valueOf(edte)));
        ps.setInt(3, qnt);
        ps.setInt(4, controlflg);
        ps.setString(5, controlmsg);

        ps.executeUpdate();
    }


    public static CDate foundCD(LocalDate fdate) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String sDate = fdate.plusDays(-7).format(formatters);
        String eDate = fdate.format(formatters);
        CDate cDate = new CDate(sDate,eDate);
        return cDate;
    }


    public static LocalDate convertDate(Date dateToConvert) {
        return new java.sql.Timestamp(dateToConvert.getTime()).toLocalDateTime().toLocalDate();
    }

    public static List<String> reqDateList(Date startTime, Date endTime, String form){
        List<String> dateStringsLst = convertDate(startTime).datesUntil(convertDate(endTime))
                .map(date -> date.format(DateTimeFormatter.ofPattern(form)))
                .collect(Collectors.toList());
        return dateStringsLst;
    }

    public static boolean editDevice(String url, int tcpa, String des, int crt, int app, int agr, int exe, int lifetime, int savetime, MultipartFile fileupload, String Comment, int Newtmp, boolean Multisign) {
        try {
            Connection dbConnection = DriverManager.getConnection(url);
            PreparedStatement ps = dbConnection.prepareStatement(
                    "UPDATE Tcpatmst " +
                            "SET des = ?, crtdoctyp = ?, aprdoctyp = ?, agrdoctyp = ?, exedoctyp = ?, lifetime = ?, savetime = ?, docx = ?, Comment = ?, Newtmp = ?, Multisign = ? " +
                            "WHERE tcpat = " + tcpa);
            ps.setString(1, des);
            ps.setInt(2, crt);
            ps.setInt(3, app);
            ps.setInt(4, agr);
            ps.setInt(5, exe);
            ps.setInt(6, lifetime);
            ps.setInt(7, savetime);
            ps.setBytes(8, fileupload.getBytes());
            ps.setString(9, Comment);
            ps.setInt(10, Newtmp);
            ps.setBoolean(11, Multisign);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    return true;
    }

    public static boolean setDocxTcpoahdr(String url, int tcpoa, XWPFDocument doc) {
        try {
            Connection dbConnection = DriverManager.getConnection(url);
            PreparedStatement ps = dbConnection.prepareStatement(
                    "UPDATE Tcpoahdr " +
                            "SET Docx = ? " +
                            "WHERE tcpoa = " + tcpoa);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            doc.write(out);

            ps.setBytes(1, out.toByteArray());
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public static boolean editDevice(String url, int tcpa, String des, int crt, int app, int agr, int exe, int lifetime, int savetime, String Comment, int Newtmp, boolean Multisign) {
        try {
            Connection dbConnection = DriverManager.getConnection(url);
            PreparedStatement ps = dbConnection.prepareStatement(
                    "UPDATE Tcpatmst " +
                            "SET des = ?, crtdoctyp = ?, aprdoctyp = ?, agrdoctyp = ?, exedoctyp = ?, lifetime = ?, savetime = ?, Comment = ?, Newtmp = ?, Multisign = ? " +
                            "WHERE tcpat = " + tcpa);
            ps.setString(1, des);
            ps.setInt(2, crt);
            ps.setInt(3, app);
            ps.setInt(4, agr);
            ps.setInt(5, exe);
            ps.setInt(6, lifetime);
            ps.setInt(7, savetime);
            ps.setString(8, Comment);
            ps.setInt(9, Newtmp);
            ps.setBoolean(10, Multisign);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }


    public static boolean addTcpatmst(String url, String entby, int tcpa, String des, int crt, int app, int agr, int exe, int lifetime, int savetime, MultipartFile fileupload, String Comment, int Newtmp, boolean Multisign) {
        try {
            Connection dbConnection = DriverManager.getConnection(url);
            PreparedStatement ps = dbConnection.prepareStatement(
                    "INSERT INTO Tcpatmst " +
                            "(entby, tcpat, des, crtdoctyp, aprdoctyp, agrdoctyp, exedoctyp, lifetime, savetime, docx, entdte, Comment, Newtmp, Multisign) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
            ps.setString(1, entby);
            ps.setInt(2, tcpa);
            ps.setString(3, des);
            ps.setInt(4, crt);
            ps.setInt(5, app);
            ps.setInt(6, agr);
            ps.setInt(7, exe);
            ps.setInt(8, lifetime);
            ps.setInt(9, savetime);
            ps.setBytes(10, fileupload.getBytes());
            ps.setDate(11, new java.sql.Date(new java.util.Date().getTime()));
            ps.setString(12, Comment);
            ps.setInt(13, Newtmp);
            ps.setBoolean(14, Multisign);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public static class DocxReplace{
        String s1;
        String s2;

        public DocxReplace(String s1, String s2) {
            this.s1 = s1;
            this.s2 = s2;
        }
    }

    public static class URD{
        public Map<String, String> sl;
        public XWPFDocument doc;

        public URD(Map<String, String> sl) {
            this.sl = sl;
        }
    }

    public static XWPFDocument setImgDocx(XWPFDocument doc, String teg, String html, int cntPng) throws Exception {
        ByteArrayOutputStream os = convertHtmlToImage(html);
        InputStream is = new ByteArrayInputStream(os.toByteArray());

        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if(text != null && text.contains(teg)) {
                        r.setText(r.text().replace(teg, ""), 0);
                        r.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, "imgFile", Units.toEMU(263), Units.toEMU(75*cntPng));
                        break;
                    }
                }
            }
        }
        return doc;
    }

    public static ByteArrayOutputStream convertHtmlToImage(String imageHtml) throws IOException {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml (imageHtml); // Он также может быть загружен в соответствии с html-ссылкой на loadUrl
        //Thread.sleep(1000); // Иногда происходит задержка загрузки изображений, поэтому установите задержку здесь
        imageGenerator.getBufferedImage();
        //Thread.sleep(2000);
        BufferedImage bi = imageGenerator.getBufferedImage();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bi, "PNG", os);

//        ImageIO.write(bi, "PNG", new File("C:/GPS/698589.png"));

        return os;
    }


    public static boolean foundInDocx(XWPFDocument doc, String teg){
        boolean res = false;
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if(text != null && text.contains(teg)) {
                        res = true;
                        break;
                    }
                }
            }
        }
            return res;
    }

    public static URD updateDocx(XWPFDocument doc, List<DocxReplace> docxReplaces){
        URD res = new URD(new HashMap<>());
        try {
            for (DocxReplace dr:docxReplaces) {
                for (XWPFParagraph p : doc.getParagraphs()) {
                    List<XWPFRun> runs = p.getRuns();
                    if (runs != null) {
                        String prev = "";
                        for (XWPFRun r : runs) {
                            String text = r.getText(0);
                            if(text != null && text.contains("Таб"))
                                System.out.println(text + ";  " + prev);
                            if (text != null && text.contains(dr.s1)) {
                                res.sl.put(dr.s2, prev);
                                text = text.replace(dr.s1, dr.s2);
                                r.setText(text, 0);
                            }
                            prev = r.getText(0);
                        }
                    }
                }
                for (XWPFTable tbl : doc.getTables()) {
                    for (XWPFTableRow row : tbl.getRows()) {
                        for (XWPFTableCell cell : row.getTableCells()) {
                            for (XWPFParagraph p : cell.getParagraphs()) {
                                String prev = "";
                                for (XWPFRun r : p.getRuns()) {
                                    String text = r.getText(0);
                                    if (text != null && text.contains(dr.s1)) {
                                        System.out.println(prev);
                                        text = text.replace(dr.s1, dr.s2);
                                        r.setText(text,0);
                                    }
                                    prev = r.getText(0);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        res.doc = doc;
        return res;
    }

    public static boolean addTcpatmst(String url, String entby, int tcpa, String des, int crt, int app, int agr, int exe, int lifetime, int savetime, String Comment, int Newtmp, boolean Multisign) {
        try {
            Connection dbConnection = DriverManager.getConnection(url);
            PreparedStatement ps = dbConnection.prepareStatement(
                    "INSERT INTO Tcpatmst " +
                            "(entby, tcpat, des, crtdoctyp, aprdoctyp, agrdoctyp, exedoctyp, lifetime, savetime, entdte, Comment, Newtmp, Multisign) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
            ps.setString(1, entby);
            ps.setInt(2, tcpa);
            ps.setString(3, des);
            ps.setInt(4, crt);
            ps.setInt(5, app);
            ps.setInt(6, agr);
            ps.setInt(7, exe);
            ps.setInt(8, lifetime);
            ps.setInt(9, savetime);
            ps.setDate(10, new java.sql.Date(new java.util.Date().getTime()));
            ps.setString(11, Comment);
            ps.setInt(12, Newtmp);
            ps.setBoolean(13, Multisign);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public static MainController.Tcpatmst getTcpatmst(String url, int Tcpat) {
        MainController.Tcpatmst res = new MainController.Tcpatmst();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT Tcpat, Des, Entby, Lstchgby, Entdte, Lstchgdte, Rcdsts, Crtdoctyp, Agrdoctyp, Aprdoctyp, Exedoctyp, Lifetime, Savetime, Docx, Comment, Newtmp, Multisign FROM Tcpatmst WHERE Tcpat = " + Tcpat;
            ResultSet rs = conn.createStatement().executeQuery(sql);


            rs.next();
            res.setTcpat(rs.getInt("Tcpat"));
            res.setDes(rs.getString("Des"));
            res.setEntby(rs.getString("Entby"));
            res.setLstchgby(rs.getString("Lstchgby"));
            res.setEntdte(rs.getDate("Entdte"));
            res.setLstchgdte(rs.getDate("Lstchgdte"));
            res.setRcdsts(rs.getInt("Rcdsts"));
            res.setCrtdoctyp(rs.getInt("Crtdoctyp"));
            res.setAgrdoctyp(rs.getInt("Agrdoctyp"));
            res.setAprdoctyp(rs.getInt("Aprdoctyp"));
            res.setExedoctyp(rs.getInt("Exedoctyp"));
            res.setLifetime(rs.getInt("Lifetime"));
            res.setSavetime(rs.getInt("Savetime"));
            res.setDocx(rs.getBytes("Docx"));
            res.setComment(rs.getString("Comment"));
            res.setNewtmp(rs.getInt("Newtmp"));
            res.setMultisign(rs.getBoolean("Multisign"));

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return res;
    }

    public static List<MainController.Tcpatmst_> getTcpatmstLstWO(String url) {
        List<MainController.Tcpatmst_> resLst = new ArrayList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT Tcpat, Des, Entby, Lstchgby, Entdte, Lstchgdte, Rcdsts, Crtdoctyp, Agrdoctyp, Aprdoctyp, Exedoctyp, Lifetime, Savetime FROM Tcpatmst WHERE Crtdoctyp=4";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()) {
                MainController.Tcpatmst_ res = new MainController.Tcpatmst_();
                res.setTcpat(rs.getInt("Tcpat"));
                res.setDes(rs.getString("Des"));
                res.setEntby(rs.getString("Entby"));
                res.setLstchgby(rs.getString("Lstchgby"));
                res.setEntdte(rs.getDate("Entdte"));
                res.setLstchgdte(rs.getDate("Lstchgdte"));
                res.setRcdsts(rs.getInt("Rcdsts"));
                res.setCrtdoctyp(rs.getInt("Crtdoctyp"));
                res.setAgrdoctyp(rs.getInt("Agrdoctyp"));
                res.setAprdoctyp(rs.getInt("Aprdoctyp"));
                res.setExedoctyp(rs.getInt("Exedoctyp"));
                res.setLifetime(rs.getInt("Lifetime"));
                res.setSavetime(rs.getInt("Savetime"));

                resLst.add(res);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return resLst;
    }

    public static List<MainController.Tcpatmst_> getTcpatmstLst(String url) {
        List<MainController.Tcpatmst_> resLst = new ArrayList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT Tcpat, Des, Entby, Lstchgby, Entdte, Lstchgdte, Rcdsts, Crtdoctyp, Agrdoctyp, Aprdoctyp, Exedoctyp, Lifetime, Savetime FROM Tcpatmst";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()) {
                MainController.Tcpatmst_ res = new MainController.Tcpatmst_();
                res.setTcpat(rs.getInt("Tcpat"));
                res.setDes(rs.getString("Des"));
                res.setEntby(rs.getString("Entby"));
                res.setLstchgby(rs.getString("Lstchgby"));
                res.setEntdte(rs.getDate("Entdte"));
                res.setLstchgdte(rs.getDate("Lstchgdte"));
                res.setRcdsts(rs.getInt("Rcdsts"));
                res.setCrtdoctyp(rs.getInt("Crtdoctyp"));
                res.setAgrdoctyp(rs.getInt("Agrdoctyp"));
                res.setAprdoctyp(rs.getInt("Aprdoctyp"));
                res.setExedoctyp(rs.getInt("Exedoctyp"));
                res.setLifetime(rs.getInt("Lifetime"));
                res.setSavetime(rs.getInt("Savetime"));

                resLst.add(res);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return resLst;
    }

    public static byte[] getDocx(String url, int Tcpat) {
        byte[] res = new byte[]{};
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT Docx FROM Tcpatmst WHERE Tcpat = " + Tcpat;
            ResultSet rs = conn.createStatement().executeQuery(sql);


            rs.next();
            res = rs.getBytes(1);

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return res;
    }

    public static String getTabnum(String url, String psn) {
        String res = "";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT Tabnum from Psnbrn WHERE Psn = '" + psn + "'";
            ResultSet rs = conn.createStatement().executeQuery(sql);


            rs.next();
            res = rs.getString(1);

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return res;
    }

    public static List<DocElem> getDocElem(String url, Date startTime, Date endTime, String dep, String tmp, String fio, int control, boolean psn, String p) {
        String psnStr = " ";
        if(!psn){
            psnStr += "AND (b.Crtpsnsign = '"+p+"'OR b.Agrpsnsign = '"+p+"'OR b.Apppsnsign = '"+p+"'OR b.Exepsnsign = '"+p+"') ";
        }

        List<DocElem> res = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String filtr = " ";
        if(dep.length() > 0)
            filtr += " and d.Brn = " + dep.split("_")[0] + " and d.Cln = " + dep.split("_")[1];
        if(tmp.length() > 0)
            filtr += " and a.Tcpat = " + tmp;
        if(fio.length() > 0)
            filtr += " and b.Tcpoa = " + fio;
        if(control != -1)
            filtr += " and b.Сontrolflg = " + control;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
            String sql = "select b.Tcpoa, b.Actdte, ISNULL(d.Des, '') as Dep, a.Des as Template, b.Crtpsnsign, b.Crtpsndessign, b.Crtdtesign, b.Agrpsnsign, b.Agrpsndessign, b.Agrdtesign, b.Apppsnsign, b.Apppsndessign, b.Appdtesign, b.Exepsnsign, b.Exepsndessign, b.Exedtesign, b.Entdte, b.Сontrolflg, b.Сontrolmsg, a.Multisign " +
                    "from Tcpoahdr b " +
                    " left join Tcpatmst a on b.Tcpat = a.Tcpat " +
                    " left join Psnbrn c on b.Psn=c.Psn and b.Tabnum=c.Tabnum and c.Rcdsts < 9 " +
                    " left join Clnmst d on c.Cln=d.Cln and c.Ptnbrn=d.Brn " +
                    "WHERE b.Actdte > '"+sdf.format(startTime)+"' AND b.Actdte < '"+sdf.format(endTime) +"'"+psnStr+
                    filtr;

            ResultSet rs = conn.createStatement().executeQuery(sql);


            while (rs.next()) {
                int Tcpoa = rs.getInt("Tcpoa");
                Date Actdte = new java.util.Date(rs.getTimestamp("Actdte").getTime());
                String Dep = rs.getString("Dep");
                String Template = rs.getString("Template");
                int Сontrolflg = rs.getInt("Сontrolflg");
                String Сontrolmsg = rs.getString("Сontrolmsg");


                String Apppsndessign = rs.getString("Apppsndessign");
                String Agrpsndessign = rs.getString("Agrpsndessign");
                String Crtpsndessign = rs.getString("Crtpsndessign");
                String Exepsndessign = rs.getString("Exepsndessign");

                String Apppsnsign = rs.getString("Apppsnsign");
                String Agrpsnsign = rs.getString("Agrpsnsign");
                String Crtpsnsign = rs.getString("Crtpsnsign");
                String Exepsnsign = rs.getString("Exepsnsign");

                Date Appdtesign = new java.util.Date(rs.getTimestamp("Appdtesign").getTime());
                Date Agrdtesign = new java.util.Date(rs.getTimestamp("Agrdtesign").getTime());
                Date Crtdtesign = new java.util.Date(rs.getTimestamp("Crtdtesign").getTime());
                Date Exedtesign = new java.util.Date(rs.getTimestamp("Exedtesign").getTime());

                boolean checkbox = rs.getBoolean("Multisign");


                res.add(new DocElem(Tcpoa, Actdte, Dep, Template, Crtpsnsign, Crtpsndessign, Crtdtesign, Agrpsnsign, Agrpsndessign, Agrdtesign, Apppsnsign, Apppsndessign, Appdtesign, Exepsnsign, Exepsndessign, Exedtesign, Сontrolflg, Сontrolmsg, checkbox));
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return res;
    }

    public static List<DocElem> getDocElemPage(String url, String order, Date startTime, Date endTime, int i1, int i2, String dep, String tmp, String fio, int control, boolean psn, String p) {
        String psnStr = " ";
        if(!psn){
            psnStr += "AND (b.Crtpsnsign = '"+p+"'OR b.Agrpsnsign = '"+p+"'OR b.Apppsnsign = '"+p+"'OR b.Exepsnsign = '"+p+"') ";
        }

        List<DocElem> res = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String filtr = " ";
        if(dep.length() > 0)
            filtr += " and d.Brn = " + dep.split("_")[0] + " and d.Cln = " + dep.split("_")[1];
        if(tmp.length() > 0)
            filtr += " and a.Tcpat = " + tmp;
        if(fio.length() > 0)
            filtr += " and b.Tcpoa = " + fio;
        if(control != -1)
            filtr += " and b.Сontrolflg = " + control;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
            String sql = "select b.Tcpoa, b.Actdte, ISNULL(d.Des, '') as Dep, a.Des as Template, b.Crtpsnsign, b.Crtpsndessign, b.Crtdtesign, b.Agrpsnsign, b.Agrpsndessign, b.Agrdtesign, b.Apppsnsign, b.Apppsndessign, b.Appdtesign, b.Exepsnsign, b.Exepsndessign, b.Exedtesign, b.Entdte, b.Сontrolflg, b.Сontrolmsg, a.Multisign " +
                    "from Tcpoahdr b " +
                    " left join Tcpatmst a on b.Tcpat = a.Tcpat " +
                    " left join Psnbrn c on b.Psn=c.Psn and b.Tabnum=c.Tabnum and c.Rcdsts < 9 " +
                    " left join Clnmst d on c.Cln=d.Cln and c.Ptnbrn=d.Brn " +
                    "WHERE b.Actdte > '"+sdf.format(startTime)+"' AND b.Actdte < '"+sdf.format(endTime) +"'"+psnStr+
                    filtr +
                    " ORDER BY " + order +
                    " offset " + i1 + " rows " +
                    "fetch next " + i2 + " rows only";
            ResultSet rs = conn.createStatement().executeQuery(sql);


            while (rs.next()) {
                int Tcpoa = rs.getInt("Tcpoa");
                Date Actdte = new java.util.Date(rs.getTimestamp("Actdte").getTime());
                String Dep = rs.getString("Dep");
                String Template = rs.getString("Template");
                int Сontrolflg = rs.getInt("Сontrolflg");
                String Сontrolmsg = rs.getString("Сontrolmsg");


                String Apppsndessign = rs.getString("Apppsndessign");
                String Agrpsndessign = rs.getString("Agrpsndessign");
                String Crtpsndessign = rs.getString("Crtpsndessign");
                String Exepsndessign = rs.getString("Exepsndessign");

                String Apppsnsign = rs.getString("Apppsnsign");
                String Agrpsnsign = rs.getString("Agrpsnsign");
                String Crtpsnsign = rs.getString("Crtpsnsign");
                String Exepsnsign = rs.getString("Exepsnsign");

                Date Appdtesign = new java.util.Date(rs.getTimestamp("Appdtesign").getTime());
                Date Agrdtesign = new java.util.Date(rs.getTimestamp("Agrdtesign").getTime());
                Date Crtdtesign = new java.util.Date(rs.getTimestamp("Crtdtesign").getTime());
                Date Exedtesign = new java.util.Date(rs.getTimestamp("Exedtesign").getTime());

                boolean checkbox = rs.getBoolean("Multisign");


                res.add(new DocElem(Tcpoa, Actdte, Dep, Template, Crtpsnsign, Crtpsndessign, Crtdtesign, Agrpsnsign, Agrpsndessign, Agrdtesign, Apppsnsign, Apppsndessign, Appdtesign, Exepsnsign, Exepsndessign, Exedtesign, Сontrolflg, Сontrolmsg, checkbox));
           }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return res;
    }

    public static String getPsnLogin(String url) {
        String res = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usr = auth.getName();

        try {
            Connection conn = DriverManager.getConnection(url);
            ResultSet rs = conn.createStatement().executeQuery("SELECT Psn FROM Psnmst WHERE Email = '"+usr+"'");

            rs.next();

            res = rs.getString("Psn");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }


    static String getMillageColor(int speed) {
        if (speed >= 110)
            return "background-color: #ff00ff; text-align: center";
        else if (speed >= 90)
            return "background-color: #ff0000; text-align: center";
        else if (speed >= 60)
            return "background-color: #ff8000; text-align: center";
        else if (speed >= 40)
            return "background-color: #d0d000; text-align: center";
        else if (speed >= 0)
            return "background-color: #008000; text-align: center";
        else
            return "background-color: #8080FF; text-align: center";
//        if (speed >= 110)
//            return "color: Fuchsia";
//        else if (speed >= 90)
//            return "color: Red";
//        else if (speed >= 60)
//            return "color: DarkOrange";
//        else if (speed >= 40)
//            return "color: Olive";
//        else if (speed >= 0)
//            return "color: Green";
//        else
//            return "color: SlateBlue";
    }


    public static List<DevInfo> getDevInfLst(DevMapper devMapper) {
        List<DevInfo> res = new ArrayList<DevInfo>();
        res = devMapper.getDevInfo();

        return res;
    }
}
