package com.userservice.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//class to get in some dummy data

@Component
public class InitializationComponent {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void init(){

        userRepository.deleteAll();

        User user=new User();
        user.setEmail("user1.Mail@gmx.at");
        user.setName("user1");
        user.setEmail_verified("no");
        user.setUpdated_at("yesterday");
        user.setPicture("test");
        user.setNickname("u1");
        user.setSub("testSub");
        userRepository.save(user);

        user=new User();
        user.setEmail("user2.Mail@gmx.at");
        user.setName("user2");
        user.setEmail_verified("no");
        user.setUpdated_at("today");
        user.setPicture("test2");
        user.setNickname("u2");
        user.setSub("testSub2");
        userRepository.save(user);
    }
}
