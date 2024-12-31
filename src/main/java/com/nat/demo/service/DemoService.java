package com.nat.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nat.demo.entity.DemoEntity;
import com.nat.demo.entity.UserEntity;
import com.nat.demo.repository.DemoRepository;

@Component
public class DemoService {

    @Autowired
    private DemoRepository demoRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveDemoEntry(DemoEntity demoEntity, String username) {
        try {
            UserEntity user = userService.findByUsername(username);
            demoEntity.setCreatedDate(LocalDateTime.now());
            DemoEntity saved = demoRepository.save(demoEntity);
            // user.setUsername(null);
            user.getDemoEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            // System.out.println(e);
            throw new RuntimeException("An error occured while saving demo entry");
        }
    }

    public void saveDemoEntry(DemoEntity demoEntity) {
        demoRepository.save(demoEntity);
    }

    public List<DemoEntity> getAllDemoEntries() {
        return demoRepository.findAll();
    }

    public void deleteDemoEntry(String username, ObjectId id) {
        UserEntity user = userService.findByUsername(username);

        // Remove the demo entry from the user's list of demo entries
        user.getDemoEntries().removeIf(demo -> demo.getId().equals(id));

        userService.saveUser(user);
        demoRepository.deleteById(id);
    }

    public Optional<DemoEntity> getDemoEntry(ObjectId id) {
        return demoRepository.findById(id);
    }
}
