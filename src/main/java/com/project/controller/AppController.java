package com.project.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@EnableAutoConfiguration
@RestController
@RequestMapping("/api/v1/*")
public class AppController extends WebMvcConfigurerAdapter {
	
}
