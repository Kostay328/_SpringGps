package ru.ining.gps.db;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Hello world!
 */
public class AutoGpsLib {
//    static void info(People people) {
//        System.out.println("Name = " + people.getName());
//        System.out.println(String.format("Age = %s", people.getAge()));   RecordsMapper
//    }


    //private static String URL = "jdbc:sqlserver://localhost;user=sa;password=89037637168;";




//    public static void start() {
//        Timestamp stamps = new Timestamp(Date.valueOf("2021-04-01").getTime());
//        Date dates = new Date(stamps.getTime());
//
//        Timestamp stampe = new Timestamp(Date.valueOf("2021-04-31").getTime());
//        Date datee = new Date(stampe.getTime());
//        //System.out.println(dateFormat.format(datee));
//
//        //Timestamp stamp = new Timestamp(1601445582000L);
////        Date date = new Date(stamp.getTime());
//        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss.SSS");
//        Date date = new Date(System.currentTimeMillis());
//        System.out.println(formatter.format(date));
//        try {
//            getRun("У006ВР790", dates, datee);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }


    public static List<Result> process(Integer waybillTime) throws Exception {
        List<Result> reslist = new ArrayList<>();
        Properties prop = new Properties();
        //File file = new File(AutoGpsLib.class.getClassLoader().getResource("config.properties").getFile());
        //try(FileInputStream fileInputStream = new FileInputStream("C:\\GPS\\config.properties");) {
        try(InputStream fileInputStream = AutoGpsLib.class.getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(fileInputStream);

            System.out.println(GetMillage.getRun());

//            String uri = prop.getProperty("ms.uri");
////            //Integer waybillTime = Integer.parseInt(prop.getProperty("daysOffset"));
////            System.out.println(uri + "   " + waybillTime);
////            AutoparkWaybill autoparkWaybill = new AutoparkWaybill(uri);
////            List<PL> pll = autoparkWaybill.waybill(waybillTime);
////            for (PL p:pll) {
////                Result res = new Result();
////
////                BigDecimal run = GetMillage.getRun(p.getRegnum(), p.getLvdtetimpl(), p.getRtdtetimpl());
////
////                res.setRegnum(p.getRegnum());
////                res.setRunGPS(run);
////
////                if(res.getRunGPS().compareTo(BigDecimal.valueOf(0.0)) == 1) {
////                    reslist.add(res);
////                    System.out.println(res.getRegnum() + "  " + res.getRunGPS());
////                }
//////                if (run.compareTo(nullDec) > 0) {
//////                    PL localP = p;
//////                    localP.setRunGPS(run);
//////                    autoparkWaybill.saveCarMillage(localP);
//////                }
////            }
        } catch (IOException e) {
            System.out.println("Ошибка в программе: файл не обнаружен");
            e.printStackTrace();
        }
        return reslist;
    }
}
