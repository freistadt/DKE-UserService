package com.userservice.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
