package com.utn.tacs.eventmanager.dao;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class EventList {

    private @Id @GeneratedValue Long id;
    private @NotNull Date creationDate;
    private String name;
    private @ElementCollection(fetch = FetchType.EAGER) Set<Long> events;
    private @ManyToOne User user;

    public EventList(String name){
        this.name = name;
    }
}
