package com.kane.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter@Setter
public class User {
    private Long id;
    private String username;
    private  String email;
    private  String password_hash;
    private Timestamp created_at;

    public User(){}

    public User(Long id, String username, String email, String password_hash, Timestamp created_at) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
        this.created_at = created_at;
    }
}
