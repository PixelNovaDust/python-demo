package com.nat.demo.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nat.demo.entity.DemoEntity;
import com.nat.demo.entity.UserEntity;
import com.nat.demo.service.DemoService;
import com.nat.demo.service.UserService;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllUserDemo(@PathVariable String username) {
        try {
            UserEntity user = userService.findByUsername(username);
            // get all demo entries for the user have key "demoEntries"
            List<DemoEntity> all = user.getDemoEntries();
            if (all == null || all.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(all);            
        } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }

    @GetMapping("/{username}/{id}")
    public ResponseEntity<List<DemoEntity>> getAllDemoEntries(@PathVariable String username,
            @PathVariable ObjectId id) {
        try {
            UserEntity user = userService.findByUsername(username);
            List<DemoEntity> entries = user.getDemoEntries();
            if (entries == null || entries.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            List<DemoEntity> filteredEntries = entries.stream().filter(entry -> entry.getId().equals(id)).toList();
            if (filteredEntries.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(filteredEntries);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{username}")
    public ResponseEntity<String> addEntryOfUser(@PathVariable String username, @RequestBody DemoEntity demoEntity) {
        try {
            demoService.saveDemoEntry(demoEntity, username);
            return ResponseEntity.ok().body("Entry created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
