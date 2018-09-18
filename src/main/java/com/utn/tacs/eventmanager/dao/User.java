package com.utn.tacs.eventmanager.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class User {

    private @Id @GeneratedValue Long id;
    private @Column(unique = true) String username;
    private String password;
    private Date lastLogin;
    private @OneToMany(mappedBy = "user") List<EventList> eventsLists;
    private @OneToMany(mappedBy = "user") List<Alarm> alarms;

    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }
}
