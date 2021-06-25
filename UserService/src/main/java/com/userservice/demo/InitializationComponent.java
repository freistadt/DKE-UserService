package com.userservice.demo;

import com.userservice.demo.model.User;
import com.userservice.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitializationComponent {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void init() {
        //userRepository.deleteAll();

//        User user3 = new User();
//        user3.setEmail("user3.Mail@gmx.at");
//        user3.setName("user3");
//        user3.setEmail_verified("no");
//        user3.setUpdated_at("yesterday");
//        user3.setPicture("test");
//        user3.setNickname("u3");
//        user3.setSub("testSub");
//
//        if(!userRepository.existsById(user3.getNickname())){
//            System.out.println("new user added");
//            userRepository.save(user3);
//            send_to_neo(user3);
//        } else {
//            System.out.println("user already exists");
//        }
//
//        System.out.println(userRepository.toString());
//        System.out.println(userRepository.count());
//
//        for(User u: userRepository.findAll()) {
//            System.out.println(u);
//        }
    }
}
