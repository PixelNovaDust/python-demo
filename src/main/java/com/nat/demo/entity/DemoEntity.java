package com.nat.demo.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "demo")
@Data
@NoArgsConstructor
public class DemoEntity {

    @Id
    private ObjectId id;
    @NonNull 
    private String demoTitle; 
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
