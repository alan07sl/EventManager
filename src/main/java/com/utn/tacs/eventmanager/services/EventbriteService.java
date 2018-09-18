package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.services.dto.EventsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EventbriteService {

    @Value("${eventbrite.url}")
    private String eventbriteURL;

    @Value("${eventbrite.token}")
    private String token;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

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

    public Map<String,Object> getEvent(Long id) throws CustomException{
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(eventbriteURL + "/events/"+id)
                .queryParam("token", token);
        try {
            return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, Map.class).getBody();
        } catch(HttpClientErrorException e) {
            throw new CustomException(e.getMessage(),e.getLocalizedMessage(),e.getStatusCode());
        }
    }

    public List<Map<String,Object>> getEvents(Iterable<Long> eventsIds) throws CustomException {

        List<ListenableFuture<ResponseEntity<Map>>> responseFutures = new ArrayList<>();
        for (Long id : eventsIds) {
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromUriString(eventbriteURL + "/events/"+id)
                    .queryParam("token", token);

            ListenableFuture<ResponseEntity<Map>> responseEntityFuture
                    = asyncRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, Map.class);
            responseFutures.add(responseEntityFuture);
        }

        List<Map<String,Object>> listOfResponses = new ArrayList<>();
        for (ListenableFuture<ResponseEntity<Map>> future: responseFutures) {
            try {
                Map response = future.get().getBody();
                listOfResponses.add(response);
            } catch (Exception e) {
                throw new CustomException(e.getMessage(),e.getLocalizedMessage(),
                        e instanceof HttpClientErrorException ? ((HttpClientErrorException)e).getStatusCode(): HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return listOfResponses;
    }

}
