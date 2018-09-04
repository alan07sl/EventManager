package com.utn.tacs.eventmanager.controllers.dto;


import lombok.Data;

@Data
public class EventDTO {

    private Integer id;

    public void setId(Integer idParam){
        id = idParam;
    }

}
