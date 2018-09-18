package com.utn.tacs.eventmanager.controllers.dto;


import lombok.Data;

import java.util.Date;

@Data
public class UserStatsDTO {

    private String username;
    private int alarms;
    private int eventsLists;
    private Date lastLogin;

}
