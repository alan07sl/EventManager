package com.utn.tacs.eventmanager.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {

    private @Id @GeneratedValue Long id;
    private @Column(unique = true) String username;
    private String password;
    @OneToMany
    @JoinColumn(name = "F_ALARM_ID")
    private List<Alarm> alarms = new ArrayList<>();
    @OneToMany
    private List<Event> events = new ArrayList<>();

    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }
}
