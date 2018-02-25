package com.fugener.rest;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class FugenerRestApplication {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello Fugeners!";
    }
    
	public static void main(String[] args) {
		SpringApplication.run(FugenerRestApplication.class, args);
	}
}
