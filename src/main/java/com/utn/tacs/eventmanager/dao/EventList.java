package com.utn.tacs.eventmanager.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class EventList {

    private @Id
    String id;
    private @NotNull Date creationDate;
    private String name;
    private Set<Long> events;
    private String userId;

    public EventList(String name){
        this.name = name;
    }

    public Set<Long> getEvents() {
        if(events == null) {
            events = new HashSet<>();
        }
        return events;
    }
}
