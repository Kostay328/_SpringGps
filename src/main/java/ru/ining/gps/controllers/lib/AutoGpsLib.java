package ru.ining.gps.controllers.lib;

import ru.ining.gps.entity.CarMillage;
import ru.ining.gps.entity.DevInfo;
import ru.ining.gps.entity.Marker;
import ru.ining.gps.mappers.CarMapper;
import ru.ining.gps.mappers.DevMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
