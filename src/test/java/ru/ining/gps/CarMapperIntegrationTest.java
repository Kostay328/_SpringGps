package ru.ining.gps;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.ining.gps.entity.Car;
import ru.ining.gps.entity.CarMillage;
import ru.ining.gps.mappers.CarMapper;

import java.util.List;

@SpringBootTest
public class CarMapperIntegrationTest {
    @Autowired
    CarMapper carMapper;

    @Test
    public void whenRecordsInDatabase_shouldReturnCarWithGivenId() {
        Car car = carMapper.getCar(1L);

        Assert.notNull(car, "");
        Assert.isTrue(car.getId() == 1, "");
    }

    @Test
    public void checkCarList() {
        List<Car> cars = carMapper.getCars();

        Assert.notNull(cars, "");
        Assert.isTrue(cars.get(0).getId() == 1, "");
        System.out.println("Всего машин в списке: " + cars.size());
    }

    @Test
    public void checkMillageList() {
        List<CarMillage> millages = carMapper.getMillage(1599445272000L, 1603445272000L);

//        Assert.notNull(millages, "");
//        Assert.isTrue(millages.get(0).getId() == 1, "");
        System.out.println("Всего машин в списке: " + millages.size());
    }
}
