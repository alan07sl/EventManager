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

@Service
public class EventbriteService {

    @Value("${eventbrite.url}")
    private String eventbriteURL;

    @Value("${eventbrite.token}")
    private String token;

    @Autowired
    private RestTemplate restTemplate;

    public EventsResponseDTO getEvents(String page, String query) throws CustomException{
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(eventbriteURL + "/events/search")
                .queryParam("token", token)
                .queryParam("q", query)
                .queryParam("page", page);
        try {
            return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, EventsResponseDTO.class).getBody();
        } catch(HttpClientErrorException e) {
            throw new CustomException(e.getMessage(),e.getLocalizedMessage(),e.getStatusCode());
        }
    }

}
