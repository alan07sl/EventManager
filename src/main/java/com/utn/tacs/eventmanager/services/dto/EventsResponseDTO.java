package com.utn.tacs.eventmanager.services.dto;

import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class EventsResponseDTO {

    public PaginatedDTO pagination;
    public List<Map<String,Object>> events;

}
