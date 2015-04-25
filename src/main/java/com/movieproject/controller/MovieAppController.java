package com.movieproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import scala.annotation.meta.setter;

import com.movieproject.dto.DemoDto;
import com.movieproject.interfaces.DemoInterface;

@EnableAutoConfiguration
@RestController
@RequestMapping("/api/v1/*")
@ImportResource("classpath:Beans.xml")
public class MovieAppController extends WebMvcConfigurerAdapter {

	@Autowired
	DemoInterface demoInterface;
	
	// User Related Operations
	@RequestMapping(value = "/demo/{username}", method = RequestMethod.GET)
	@ResponseBody
	public DemoDto demoWelcomeController(@PathVariable String username) {
		DemoDto demoDto = new DemoDto();
		demoDto.setMessage(demoInterface.printWelcomeMessage(username));
		return demoDto;
	}

}
