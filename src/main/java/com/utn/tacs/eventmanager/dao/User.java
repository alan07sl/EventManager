package com.utn.tacs.eventmanager.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class User {

    private @Id @GeneratedValue Long id;
    private @Column(unique = true) String username;
    private String password;
    private Date lastLogin;
    private Boolean isAdmin = false;
    private @OneToMany(mappedBy = "user") List<EventList> eventsLists;
    private @OneToMany(mappedBy = "user") List<Alarm> alarms;
    @OneToMany
    private List<Event> events = new ArrayList<>();

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
