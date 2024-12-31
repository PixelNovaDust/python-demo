package com.nat.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nat.demo.entity.UserEntity;
import com.nat.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try {
            UserEntity user = userService.findByUsername(username);
            if (user != null) {
                return ResponseEntity.ok().body(user);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserEntity userEntity) {
        try {
            UserEntity user = userService.findByUsername(username);
            if (user != null) {
                user.setUsername(userEntity.getUsername());
                user.setPassword(userEntity.getPassword());
                user.setUpdateDate(LocalDateTime.now());
                userService.saveUser(user);
                return ResponseEntity.ok().body("User updated successfully");
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        try {
            UserEntity user = userService.findByUsername(username);
        if(user != null){
            userService.deleteUser(user.getId());
            return ResponseEntity.ok().body("User deleted successfully");
        }
        return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
