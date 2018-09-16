package com.utn.tacs.eventmanager.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedDTO {
    @JsonProperty("object_count")
    private int objectCount;
    @JsonProperty("page_number")
    private int pageNumber;
    @JsonProperty("page_size")
    private int pageSize;
    @JsonProperty("page_count")
    private int pageCount;
    @JsonProperty("has_more_items")
    private boolean hasMoreItems;

    public boolean hasMoreItems() {
        return hasMoreItems;
    }
}
