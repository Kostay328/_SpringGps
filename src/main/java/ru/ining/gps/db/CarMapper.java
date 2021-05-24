package ru.ining.gps.db;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface CarMapper {
//    @Select("SELECT * FROM cars WHERE no = #{no}")
//    Car getCar(@Param("no") String no);

    @Select("select a.id, a.car, a.startt, a.endt, a.mileage, b.no\n" +
            "from mileage a, cars b\n" +
            "where a.car=b.id and startt>1599445272000 and startt<1603445272000\n" +
            "order by b.name, b.no, a.startt")
    List<Car> getCars();
}
