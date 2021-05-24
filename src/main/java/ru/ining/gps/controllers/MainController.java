package ru.ining.gps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ining.gps.db.AutoGpsLib;
import ru.ining.gps.mappers.CarMapper;

@Controller
public class MainController {
    final CarMapper carMapper;

    public MainController(CarMapper carMapper) {
        this.carMapper = carMapper;
    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("cars", carMapper.getCars());
        return "main";
    }

    @GetMapping("/first")
    public String first(Model model) {
        return "first";
    }

    @RequestMapping(value = { "/first" }, method = RequestMethod.POST)
    public String input(@RequestParam String wbt, Model model) {

        try {
            model.addAttribute("reslst", AutoGpsLib.process(Integer.valueOf(wbt)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "first";
    }

}
