package com.utn.tacs.eventmanager.dao;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {

    private @Id @GeneratedValue Long id;
    private @Column(unique = true) String username;
    private String password;

    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }
}
