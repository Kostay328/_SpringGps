package ru.ining.gps.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import ru.ining.gps.entity.DevInfo;
import java.util.List;

@Mapper
public interface DevMapper {

    @Select("select cars.no, cars.name, cars.id, deviceinfo.* " +
            "FROM deviceinfo " +
            "join devices ON deviceinfo.device = devices.device " +
            "join cars ON devices.car = cars.id " +
            "order by cars.name"
    )
    List<DevInfo> getDevInfo();

}
