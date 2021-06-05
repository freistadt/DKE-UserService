package com.userservice.demo;

import com.userservice.demo.model.User;
import com.userservice.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.POST)
    public long getUser() {return userRepository.count();}
}
