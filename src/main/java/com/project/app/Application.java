package com.project.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.project.controller.AppController;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application{

    public static void main(String[] args) {
    	SpringApplication.run(AppController.class, args);
    }
    
}
