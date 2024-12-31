package com.nat.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nat.demo.entity.UserEntity;
import com.nat.demo.repository.UserRepository;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserEntity userEntity) {
        userEntity.setRoles(Arrays.asList("USER"));
        userRepository.save(userEntity);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteUser(ObjectId id) {
        userRepository.deleteById(id);
    }
    
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
