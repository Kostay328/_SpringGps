package ru.ining.gps.controllers.lib;

import com.microsoft.sqlserver.jdbc.SQLServerBulkCSVFileRecord;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopyOptions;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.ining.gps.controllers.MainController;
import ru.ining.gps.entity.CarMillage;
import ru.ining.gps.entity.DevInfo;
import ru.ining.gps.entity.DocElem;
import ru.ining.gps.entity.Marker;
import ru.ining.gps.mappers.CarMapper;
import ru.ining.gps.mappers.DevMapper;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class AutoGpsLib {
    private static DecimalFormat df1 = new DecimalFormat("#.#");

    public static class MillageDuration {
        private String res;
        private String no;
        private String mileageKm;
        private double mileage;
        private Long duration;
        private Long start;
        private String color;


        public MillageDuration(double mileage, long start, long end, String color, String no) {
            this.mileage = mileage;
            this.mileageKm = df1.format(mileage/1000);
            this.start = start;
            this.duration = (end - start) / (1000 * 60);
            this.color = color;
            this.no = no;
            this.res = mileageKm + "; " + (duration/(1000*60));
        }

        public String getRes() { return res; }

        public String getNo() {
            return no;
        }

        public String getColor() {
            return color;
        }

        public double getMileage() {
            return mileage;
        }

        public String getMileageKm() {
            return mileageKm;
        }

        public long getDuration() {
            return duration;
        }

        public long getStart() {
            return start;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public static class Row {
        private final String no;
        private List<MillageDuration> lst = new ArrayList<>();

        public Row(String no) {
            this.no = no;
        }

        public String getNo() {
            return no;
        }

        public List<MillageDuration> getLst() {
            return lst;
        }

        public String getSumMileage() {
            double sum = lst.stream().map(x -> x.mileage).reduce(0.0, (x, y) -> x + y);
            return df1.format(sum/1000);
        }
    }
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


    public static String initMap() {
        String res =
                "         let map;\n" +
                        "        function initMap() {\n" +
                        "            map = new google.maps.Map(document.getElementById(\"map\"), {\n" +
                        "                 center: { lat: 55.10948, lng: 38.749325 },\n" +
                        "                zoom: 12,\n" +
                        "            });}";

        return res;
    }

    public static String creatMarkers(List<DevInfo> devInfoList) {
        String res = "function initMap() { ";
        for (DevInfo devInf : devInfoList) {
            if (devInf.isActive()) {
                res += new Marker(devInf.getName(), devInf.getLats(), devInf.getSpeed(), devInf.getDevice(), devInf.getLat(), devInf.getLng(), devInf.getCourse()).toString();
            }
        }

        return res + " }";
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


    public static String getMarkersLst(List<DevInfo> devInfoList) {
        String res =
                "         let map;\n" +
                "        function initMap() {\n" +
                "            map = new google.maps.Map(document.getElementById(\"map\"), {\n" +
                "                 center: { lat: 55.10948, lng: 38.749325 },\n" +
                "                zoom: 12,\n" +
                "            }); ";

        for (DevInfo devInf : devInfoList) {
            if (devInf.isActive()) {
                res += new Marker(devInf.getName(), devInf.getLats(), devInf.getSpeed(), devInf.getDevice(), devInf.getLat(), devInf.getLng(), devInf.getCourse()).toString();
            }
        }
        return res + " }";
    }

    public static CDate foundCD(LocalDate fdate) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String sDate = fdate.plusDays(-7).format(formatters);
        String eDate = fdate.format(formatters);
        CDate cDate = new CDate(sDate,eDate);
        return cDate;
    }

    public TreeMap<String, Row> getRun() throws IOException {
        TreeMap<String, Row> result = new TreeMap<>();
        Row row = result.get("key");
        return result;
    }

    public static void main(String[] args) {
        List<Row> report = new ArrayList<>();

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

    public static boolean editDevice(String url, int tcpa, String des, int crt, int app, int agr, int exe, int lifetime, int savetime, MultipartFile fileupload) {
        try {
            Connection dbConnection = DriverManager.getConnection(url);
            PreparedStatement ps = dbConnection.prepareStatement(
                    "UPDATE Tcpatmst " +
                            "SET des = ?, crtdoctyp = ?, aprdoctyp = ?, agrdoctyp = ?, exedoctyp = ?, lifetime = ?, savetime = ?, docx = ? " +
                            "WHERE tcpat = " + tcpa);
            ps.setString(1, des);
            ps.setInt(2, crt);
            ps.setInt(3, app);
            ps.setInt(4, agr);
            ps.setInt(5, exe);
            ps.setInt(6, lifetime);
            ps.setInt(7, savetime);
            ps.setBytes(8, fileupload.getBytes());
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    return true;
    }


    public static boolean addDevice(String url, String entby, int tcpa, String des, int crt, int app, int agr, int exe, int lifetime, int savetime, MultipartFile fileupload) {
        try {
            Connection dbConnection = DriverManager.getConnection(url);
            PreparedStatement ps = dbConnection.prepareStatement(
                    "INSERT INTO Tcpatmst " +
                            "(entby, tcpat, des, crtdoctyp, aprdoctyp, agrdoctyp, exedoctyp, lifetime, savetime, docx, entdte) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?);");
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
            String sql = "SELECT Tcpat, Des, Entby, Lstchgby, Entdte, Lstchgdte, Rcdsts, Crtdoctyp, Agrdoctyp, Aprdoctyp, Exedoctyp, Lifetime, Savetime, Docx FROM Tcpatmst WHERE Tcpat = " + Tcpat;
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

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return res;
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

    public static List<DocElem> getDocElem(String url, String order, Date startTime, Date endTime, int i1, int i2, String dep, String tmp, String fio) {
        List<DocElem> res = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String filtr = " ";
        if(dep.length() > 0)
            filtr += " and d.Brn = " + dep.split("_")[0] + " and d.Cln = " + dep.split("_")[1];
        if(tmp.length() > 0)
            filtr += " and a.Tcpat = " + tmp;
        if(fio.length() > 0)
            filtr += " and b.Tcpoa = " + fio;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
            String sql = "select b.Tcpoa, b.Actdte, ISNULL(d.Des, '') as Dep, a.Des as Template, b.Crtpsnsign, b.Crtdtesign, b.Agrpsnsign, b.Agrdtesign, b.Apppsnsign, b.Appdtesign, b.Exepsnsign, b.Exedtesign, b.Entdte " +
                    "from Tcpoahdr b " +
                    " left join Tcpatmst a on b.Tcpat = a.Tcpat " +
                    " left join Psnbrn c on b.Psn=c.Psn and b.Tabnum=c.Tabnum and c.Rcdsts < 9 " +
                    " left join Clnmst d on c.Cln=d.Cln and c.Ptnbrn=d.Brn " +
                    "WHERE b.Actdte > '"+sdf.format(startTime)+"' AND b.Actdte < '"+sdf.format(endTime) +"'"+
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


                String Apppsnsign = rs.getString("Apppsnsign");
                String Agrpsnsign = rs.getString("Agrpsnsign");
                String Crtpsnsign = rs.getString("Crtpsnsign");
                String Exepsnsign = rs.getString("Exepsnsign");

                Date Appdtesign = new java.util.Date(rs.getTimestamp("Appdtesign").getTime());
                Date Agrdtesign = new java.util.Date(rs.getTimestamp("Agrdtesign").getTime());
                Date Crtdtesign = new java.util.Date(rs.getTimestamp("Crtdtesign").getTime());
                Date Exedtesign = new java.util.Date(rs.getTimestamp("Exedtesign").getTime());


                res.add(new DocElem(Tcpoa, Actdte, Dep, Template, Crtpsnsign, Crtdtesign, Agrpsnsign, Agrdtesign, Apppsnsign, Appdtesign, Exepsnsign, Exedtesign));
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return res;
    }

    public static int getTcpoaLogin(String url) {
        int res = -1;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usr = auth.getName();

        try {
            Connection conn = DriverManager.getConnection(url);
            ResultSet rs = conn.createStatement().executeQuery("SELECT Psn FROM Psnmst WHERE Email = '"+usr+"'");

            rs.next();

            res = rs.getInt("Psn");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    static Row fillEmptyLst(Date startTime, Date endTime, Row row, String no) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        List<String> dateStringsLst = reqDateList(startTime, endTime, "yyyy.MM.dd");
        for (String date : dateStringsLst) {
            boolean dayFound = false;
            for (MillageDuration mlD : row.lst) {
                String nearestDay = formatter.format(new Date(mlD.start));
                if (date.equals(nearestDay))
                    dayFound = true;
            }
            if (!dayFound) {
                MillageDuration MD = new MillageDuration(0.0, formatter.parse(date).getTime(), formatter.parse(date).getTime(), "color: Black; text-align: center", no);
                row.lst.add(MD);
            }
        }

        return row;
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

    public static TreeMap<String, Row> process(CarMapper carMapper, Date startTime, Date endTime) throws ParseException {
        TreeMap<String, Row> res = new TreeMap<>();
        System.out.println("Время начала - " + startTime + ", время конца - " + endTime);
        List<CarMillage> millageList = carMapper.getMillage(startTime.getTime(), endTime.getTime());

        Row row = null;
        String key = "";
        String lastNo = "";
        for (CarMillage carMillage:millageList) {
            key = carMillage.getName() + carMillage.getNo();
            lastNo = carMillage.getNo();
            if (row == null || !row.no.equals(carMillage.getNo())) {

                if (row != null) {
                    fillEmptyLst(startTime, endTime, row, carMillage.getNo());
                    Collections.sort(row.lst, (a, b) -> a.start < b.start ? -1 : a.start == b.start ? 0 : 1);
                    res.put(key, row);
                }
                row = new Row(carMillage.getNo());
            }

            MillageDuration millageDuration = new MillageDuration(carMillage.getMileage(), carMillage.getStartt(), carMillage.getEndt(), getMillageColor(carMillage.getMaxspeed()), carMillage.getNo());
            row.lst.add(millageDuration);
        }
        if (row != null) {
            fillEmptyLst(startTime, endTime, row, lastNo);
            Collections.sort(row.lst, (a, b) -> a.start < b.start ? -1 : a.start == b.start ? 0 : 1);
            res.put(key, row);
        }
        return res;
    }

    public static List<DevInfo> getDevInfLst(DevMapper devMapper) {
        List<DevInfo> res = new ArrayList<DevInfo>();
        res = devMapper.getDevInfo();

        return res;
    }
}
