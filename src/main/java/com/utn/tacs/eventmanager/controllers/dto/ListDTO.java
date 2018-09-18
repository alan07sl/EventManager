package com.utn.tacs.eventmanager.controllers.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListDTO<T> {

    private Integer pageNumber;
    private Integer pageCount;
    private Long resultCount;
    private String next;
    private String prev;
    private List<T> result;
}
