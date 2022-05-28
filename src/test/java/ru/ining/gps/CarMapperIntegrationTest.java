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
}
