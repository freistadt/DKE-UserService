package com.userservice.demo;

import com.userservice.demo.model.User;
import com.userservice.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*")
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/validUser")
    public ResponseEntity<User> isValidUser(@RequestBody User loginUser) {
        System.out.println(loginUser.getUsername());

      List<User> user = userRepository.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());
      if (user.size()==1) {
          User loggedInUser = user.stream().findFirst().get();
          loggedInUser.setToken(loginUser.getToken());
          return new ResponseEntity<User>(loggedInUser, HttpStatus.OK);
      }

      return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/newUser")
    public ResponseEntity<User> createNewUser(@RequestBody User newUser) {
        System.out.println(newUser.getFirstName());
        System.out.println(newUser.getLastName());
        System.out.println(newUser.getUsername());
        System.out.println(newUser.getPassword());

        List<User> existingUser = userRepository.findByUsername(newUser.getUsername());
        if (existingUser.size()>0) {
            System.out.println("user already exists");
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        User user = userRepository.save(newUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST)
    public long getUser() {return userRepository.count();}
}
