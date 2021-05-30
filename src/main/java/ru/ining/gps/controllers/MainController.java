package ru.ining.gps.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ining.gps.controllers.lib.AutoGpsLib;
import ru.ining.gps.mappers.CarMapper;
import ru.ining.gps.mappers.DevMapper;

import java.sql.Date;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.TreeMap;

@Controller
public class MainController {
    final CarMapper carMapper;
    final DevMapper devMapper;

    public MainController(CarMapper carMapper, DevMapper devMapper) {
        this.carMapper = carMapper; this.devMapper = devMapper;
    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("cars", carMapper.getCars());
        return "main";
    }

    @GetMapping("/first")
    public String first(Model model) {
        java.util.Date nowDate = new java.util.Date();
        model.addAttribute("date", AutoGpsLib.foundCD(AutoGpsLib.convertDate(nowDate)));
        return "first";
    }

    @GetMapping("/devinfo")
    public String devinfo(Model model) {
        model.addAttribute("dev_inf_lst", AutoGpsLib.getDevInfLst(devMapper));
        return "devinfo";
    }

    @RequestMapping(value = { "/first" }, method = RequestMethod.POST)
    public String input(@RequestParam String startTime, @RequestParam String endTime, Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            TreeMap<String, AutoGpsLib.Row> car_runs = AutoGpsLib.process(carMapper, sdf.parse(startTime), sdf.parse(endTime));
            model.addAttribute("car_runs", car_runs);
            model.addAttribute("date_list", AutoGpsLib.reqDateList(sdf.parse(startTime), sdf.parse(endTime), "dd.MM.yy"));
            java.util.Date nowDate = new java.util.Date();
            model.addAttribute("date", AutoGpsLib.foundCD(AutoGpsLib.convertDate(nowDate)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "first";
    }

}
