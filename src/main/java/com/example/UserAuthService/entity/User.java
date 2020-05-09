package com.example.UserAuthService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private LocalDateTime createDate;

    @PrePersist
    public void setUpCreateDate() {
        createDate = LocalDateTime.now();
    }
}
