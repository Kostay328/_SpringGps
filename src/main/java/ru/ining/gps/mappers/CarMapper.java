package ru.ining.gps.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ru.ining.gps.entity.CarMillage;
import ru.ining.gps.entity.Car;

import java.util.List;

@Mapper
public interface CarMapper {
    @Select("SELECT * FROM cars WHERE id = #{id}")
    Car getCar(@Param("id") Long id);

    @Select("SELECT * FROM cars")
    List<Car> getCars();

    @Select("select a.id, a.car, a.startt, a.endt, a.mileage, a.maxspeed, b.no, b.name " +
            "from mileage a, cars b " +
            "where a.car=b.id and startt>#{st} and startt<#{et} " +
            "order by b.name, b.no, a.startt")
    List<CarMillage> getMillage(@Param("st") long st, @Param("et") long et);
}
