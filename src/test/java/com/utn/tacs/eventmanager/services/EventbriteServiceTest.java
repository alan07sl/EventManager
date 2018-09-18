package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.services.dto.EventsResponseDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withUnauthorizedRequest;

import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@WebMvcTest(EventbriteService.class)
public class EventbriteServiceTest {

    @Autowired
    private EventbriteService eventbriteService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Value("${eventbrite.url}")
    private String eventbriteURL;

    @Value("${eventbrite.token}")
    private String token;

    @Test
    public void shouldSuccessGetEvents() throws CustomException {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo(eventbriteURL + "/events/search?token=" + token + "&q=&page=1"))
                .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess("{ \"pagination\": {\n" +
                "\"object_count\": 76364,\n" +
                "\"page_number\": 1,\n" +
                "\"page_size\": 50,\n" +
                "\"page_count\": 200,\n" +
                "\"has_more_items\": true\n" +
                "},\n" +
                "\"events\": [] }", MediaType.APPLICATION_JSON));

        eventbriteService.getEvents("1", "");

        mockServer.verify();
    }

    @Test
    public void shouldFailGetEvents() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo(eventbriteURL + "/events/search?token=" + token + "&q=&page=1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withUnauthorizedRequest());

        try {
            eventbriteService.getEvents("1", "");
            assertThat("Get events should fail",false);
        } catch(CustomException e) {
            assertThat(e.getStatus().value(), equalTo(401));
        }
        mockServer.verify();
    }

    @Test
    public void shouldSuccessGetEvent() throws CustomException {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo(eventbriteURL + "/events/1?token=" + token))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        eventbriteService.getEvent(1L);

        mockServer.verify();
    }

    @Test
    public void shouldFailGetEvent() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo(eventbriteURL + "/events/1?token=" + token))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withUnauthorizedRequest());

        try {
            eventbriteService.getEvent(1L);
            assertThat("Get events should fail",false);
        } catch(CustomException e) {
            assertThat(e.getStatus().value(), equalTo(401));
        }
        mockServer.verify();
    }

    @Test
    public void shouldSuccessGetEventsByIds() throws CustomException {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(asyncRestTemplate);

        mockServer.expect(requestTo(eventbriteURL + "/events/1?token=" + token))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        eventbriteService.getEvents(Arrays.asList(1L));

        mockServer.verify();
    }

    @Test
    public void shouldFailGetEventsByIds() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(asyncRestTemplate);

        mockServer.expect(requestTo(eventbriteURL + "/events/1?token=" + token))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        try {
            eventbriteService.getEvents(Arrays.asList(1L));
            assertThat("Get events should fail",false);
        } catch(CustomException e) {
            assertThat(e.getStatus().value(), equalTo(500));
        }
        mockServer.verify();
    }
}
