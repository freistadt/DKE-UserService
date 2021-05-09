package com.userservice.demo;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TestApp implements CommandLineRunner {

    private final UserRepository userrepo;

    @Autowired
    public TestApp(UserRepository userrepo) {
        this.userrepo = userrepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user3=new User();
        user3.setEmail("user3.Mail@gmx.at");
        user3.setName("user3");
        user3.setEmail_verified("no");
        user3.setUpdated_at("yesterday");
        user3.setPicture("test");
        user3.setNickname("u3");
        user3.setSub("testSub");

        if(userrepo.existsById(user3.getNickname())){
            System.out.println("new user added");
            userrepo.save(user3);
        }
        else {
            System.out.println("user already exists");
        }

        System.out.println("TEST Working");
        System.out.println(userrepo.toString());
        System.out.println(userrepo.count());

        for(User u: userrepo.findAll()) {
			System.out.println(u);
		}

        User user4=new User();
        user4.setNickname("u4");
        userrepo.delete(user4);

        user_exists(user4);
    }

    public boolean user_exists(User u) {
        if(userrepo.existsById(u.getNickname())){
            System.out.println("user already exists");
            return true;
        }
        else {
            send_to_neo(u);
            userrepo.save(u);
            System.out.println("user send to neo4j " + u.getNickname());
            return false;
        }
    }

    public void send_to_neo (User u) {
        System.out.println("Sending to NEO4j" + u.toString());
    }
}
