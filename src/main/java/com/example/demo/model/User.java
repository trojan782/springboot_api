package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;


@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private int age;

    public User(String id, String name, String email, int age) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }
}