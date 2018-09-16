package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.services.dto.EventsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Clock;
import java.time.LocalDate;

@Service
public class EventbriteService {

    @Value("${eventbrite.url}")
    private String eventbriteURL;

    @Value("${eventbrite.token}")
    private String token;

    @Autowired
    private RestTemplate restTemplate;

    public EventsResponseDTO getEvents(String page, String query, boolean justRecentEvents) throws CustomException{

        UriComponentsBuilder builder;

        if(justRecentEvents) {
            LocalDate date = LocalDate.now(Clock.systemUTC()).minusDays(1);
            builder = UriComponentsBuilder
                    .fromUriString(eventbriteURL + "/events/search")
                    .queryParam("token", token)
                    .queryParam("q", query)
                    .queryParam("page", page)
                    .queryParam("date_modified.range_start", date);
        } else {
            builder = UriComponentsBuilder
                    .fromUriString(eventbriteURL + "/events/search")
                    .queryParam("token", token)
                    .queryParam("q", query)
                    .queryParam("page", page);
        }
        try {
            return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, EventsResponseDTO.class).getBody();
        } catch(HttpClientErrorException e) {
            throw new CustomException(e.getMessage(),e.getLocalizedMessage(),e.getStatusCode());
        }
    }

    public EventsResponseDTO getEvents(String page, String query) throws CustomException {
        return getEvents(page,query,false);
    }

}
