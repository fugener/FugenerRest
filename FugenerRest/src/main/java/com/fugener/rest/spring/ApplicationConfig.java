package com.fugener.rest.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "com.fugener.rest.spring.application"
})
public class ApplicationConfig {
}
