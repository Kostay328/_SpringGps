package ru.ining.gps.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ru.ining.gps.entity.Car;

import java.util.List;

@Mapper
public interface CarMapper {
    @Select("SELECT * FROM cars WHERE id = #{id}")
    Car getCar(@Param("id") Long id);

    @Select("SELECT * FROM cars")
    List<Car> getCars();
}
