package com.nat.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nat.demo.entity.UserEntity;
import com.nat.demo.service.UserService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    
    /*
     * Health Check
     */
    @GetMapping("/health-check")
    public String Health() {
        return "Health Check Passed! ✅";
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> addUser(@RequestBody UserEntity userEntity) {
        try {
            userEntity.setCreateDate(LocalDateTime.now());
            if (userService.findByUsername(userEntity.getUsername()) == null) {
                userService.saveUser(userEntity);
                return ResponseEntity.ok().body("User created successfully");
            } else {
                return ResponseEntity.badRequest().body("User already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
