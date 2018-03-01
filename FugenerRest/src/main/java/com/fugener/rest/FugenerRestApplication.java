package com.fugener.rest;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fugener.rest"})
public class FugenerRestApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(FugenerRestApplication.class, args);
	}
}
