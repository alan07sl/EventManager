package com.utn.tacs.eventmanager.controllers.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EventListDTO {

    private Integer id;
    @NotNull private String name;

}
