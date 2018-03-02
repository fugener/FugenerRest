package com.fugener.rest.spring.application;

import com.fugener.rest.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Autowired
    private DynamoConfig dynamoConfig;

    @Bean
    public UserController userController() {
        return new UserController(dynamoConfig.userRepository());
    }
}
