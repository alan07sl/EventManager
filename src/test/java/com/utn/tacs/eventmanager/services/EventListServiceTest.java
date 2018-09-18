package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.dao.EventList;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.errors.EventListNotFoundException;
import com.utn.tacs.eventmanager.errors.UserExistException;
import com.utn.tacs.eventmanager.repositories.EventListRepository;
import com.utn.tacs.eventmanager.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withUnauthorizedRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EventListServiceTest {

    @Autowired
    private EventListService eventListService;

    @Autowired
    private EventListRepository eventListRepository;

    @Test
    public void shouldCreateEventList() throws CustomException{
        eventListService.createEventList(new EventList("eventList1"));
        assertThat(eventListRepository.exists(Example.of(new EventList("eventList1"))), equalTo(true));
    }

    @Test
    public void shouldUpdateEventList() throws CustomException {
        EventList eventList = new EventList("a");
        eventList.setCreationDate(new Date());
        eventList = eventListRepository.save(eventList);
        EventList newEventList = new EventList("bb");
        eventListService.updateEventList(eventList.getId().intValue(), newEventList, null);
        assertThat(eventListRepository.exists(Example.of(newEventList)), equalTo(true));
    }

    @Test
    public void shouldFailUpdateEventListBecauseNotExist() throws CustomException {
        try {
            eventListService.updateEventList(123123, new EventList("1"), null);
            assertThat("Update should fail because event list not exist", false);
        } catch (EventListNotFoundException e) {
            assertThat("Update should fail because event list not exist", true);
        }
    }

    @Test
    public void shouldDeleteEventList() throws CustomException {
        EventList eventList = new EventList("a");
        eventList.setCreationDate(new Date());
        eventList = eventListRepository.save(eventList);
        eventListService.delete(eventList.getId(), null);
        assertThat(eventListRepository.exists(Example.of(eventList)), equalTo(false));
    }

    @Test
    public void shouldAddEventToEventList() throws CustomException{
        EventList eventList = new EventList("a");
        eventList.setCreationDate(new Date());
        eventList = eventListRepository.save(eventList);

        eventListService.addEvent(eventList.getId().intValue(), 1L, null);
        eventListService.addEvent(eventList.getId().intValue(), 2L, null);

        eventList = eventListService.findById(eventList.getId().intValue());

        assertThat(eventList.getEvents().size(), equalTo(2));
    }

    @Test
    public void shouldCountEventLists() {
        assertThat(eventListService.countEvents(new Date()), equalTo(0));
    }

    @Test
    public void shouldSearchPaginatedEventLists() {
        EventList eventList1 = new EventList("a");
        eventList1.setCreationDate(new Date());

        EventList eventList2 = new EventList("b");
        eventList2.setCreationDate(new Date());

        eventListRepository.saveAll(Arrays.asList(eventList1, eventList2));

        Page<EventList> result = eventListService.searchPaginated("", 1 , 1);
        assertThat(result.getTotalPages(), equalTo(2));
        assertThat(result.getTotalElements(), equalTo(2L));
    }

    @Test
    public void shouldCountUserInterested() {
        assertThat(eventListService.usersInterested(1L), equalTo(0));
    }


}
