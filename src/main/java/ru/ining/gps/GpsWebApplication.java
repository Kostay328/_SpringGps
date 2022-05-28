package ru.ining.gps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

@SpringBootApplication
public class GpsWebApplication {
    public static void main(String[] args) {
//        PrintStream out = null;
//        try {
//            out = new PrintStream(new FileOutputStream("output.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.setOut(out);
//        System.setErr(out);

        SpringApplication.run(GpsWebApplication.class, args);
    }
}
