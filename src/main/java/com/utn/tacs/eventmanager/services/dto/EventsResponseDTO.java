package com.utn.tacs.eventmanager.services.dto;

import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class EventsResponseDTO {

    private PaginatedDTO pagination;
    private List<Map<String,Object>> events;

}
