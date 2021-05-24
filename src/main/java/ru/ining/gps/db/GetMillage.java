package ru.ining.gps.db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

public class GetMillage {
    private String regnum;
    private Date ds;
    private Date de;

    public GetMillage(String regnum, Date ds, Date de) {
        this.regnum = regnum;
        this.ds = ds;
        this.de = de;
    }

    public static List<Car> getCarsList() throws IOException {
        List<Car> res = new ArrayList<>();
        try(InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml")) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try (SqlSession session = sqlSessionFactory.openSession()) {
                List<Car> c = session.selectList("getCars");
                res = c;
                System.out.println(c);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return res;
    }



////    public static BigDecimal getRun(String regnum, Date ds, Date de) throws IOException {
//    public static List<Result> getRun() throws IOException {
//        List<Result> result = new ArrayList<>();
//        getCarsList();
////        GetMillage getMillage = new GetMillage(regnum, ds, de);
////        Integer i = getMillage.getId();
////        List<Records> rl = getMillage.getRecords(i);
////        //System.out.println(rl.size());
////        double len = rl.stream().mapToDouble(x -> x.getLength()).sum();
//////        for (Records r:rl) {
//////            len = len + r.getLength();
//////        }
////
////        result = new BigDecimal((String.format("%.1f", 100000/1000).replace(',','.')));
//////        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss.SSS");
//////        System.out.println(formatter.format(ds) + "  " + de);
//        return result;
//    }

    //TODO:
    public static class MillageDuration {
        BigDecimal millage;
        long duration;
    }
    public static TreeMap<String, List<MillageDuration>> getRun() throws IOException {
        TreeMap<String, List<MillageDuration>> result = new TreeMap<>();
         getCarsList();
        return result;
    }
    public static void main(String[] args) {
        TreeMap<String, List<MillageDuration>> report = new TreeMap<>();

    }
}
