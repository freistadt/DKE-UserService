package com.userservice.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.demo.model.User;
import com.userservice.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    private static final HttpClient client = HttpClient.newHttpClient();

    @CrossOrigin(origins = "*")
    @GetMapping("/getUsers")
    public List<User> getAllUsers(@RequestParam(value = "loggedInUser") String loggedInUser){

        List<User> allUsers = userRepository.findAll();
        List<User> filteredList = allUsers
                .stream()
                .filter((user -> user.getUsername() != null))
                .filter((user) -> !user.getUsername().equals(loggedInUser))
                .collect(Collectors.toList());
        return filteredList;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/validUser")
    public ResponseEntity<User> isValidUser(@RequestBody User loginUser) {

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

        List<User> existingUser = userRepository.findByUsername(newUser.getUsername());
        if (existingUser.size()>0) {
            System.out.println("user already exists");
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        HttpRequest request = this.buildPutRequest(newUser.getUsername());
        HttpResponse<String> response = null;

        try{
            System.out.println("trying to send user to neo4j");
            response = this.sendRequest(request);
            int statusCode = response.statusCode();
            if(statusCode<200 || statusCode>299) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.save(newUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST)
    public long getUser() {return userRepository.count();}


    private HttpRequest buildPutRequest(String username) {
        return HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/followservice/newUser"))
                .PUT(HttpRequest.BodyPublishers.ofString(username))
                .build();
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
