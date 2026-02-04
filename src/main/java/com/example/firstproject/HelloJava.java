package com.example.firstproject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloJava {

    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Boot";
    }

}
