package com.utn.tacs.eventmanager.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class User {

    private @Id
    String id;
    private String username;
    private String password;
    private Date lastLogin;
    private Boolean isAdmin = false;

    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User (String username) {
        this.username = username;
        this.password = null;
        this.isAdmin = null;
    }
}
