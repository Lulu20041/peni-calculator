package com.example.tamozhpenies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class TamozhpeniesApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TamozhpeniesApplication.class, args);
    }


}
