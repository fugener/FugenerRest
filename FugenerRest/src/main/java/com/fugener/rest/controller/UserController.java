package com.fugener.rest.controller;

import com.fugener.rest.dynamo.model.User;
import com.fugener.rest.dynamo.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    private UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/user")
    @ResponseBody
    public String createUser() {
        User user = User.builder().id("123").name("testName123").build();
        userRepository.saveUser(user);
        List<User> userList = userRepository.getUsersByIds(Arrays.asList(user.getId()));
        return userList.get(0).getName();
    }
}
