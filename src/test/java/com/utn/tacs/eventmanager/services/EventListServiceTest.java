package com.utn.tacs.eventmanager.services;

import com.mongodb.Mongo;
import com.utn.tacs.eventmanager.dao.EventList;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.errors.EventListNotFoundException;
import com.utn.tacs.eventmanager.repositories.EventListRepository;
import com.utn.tacs.eventmanager.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(properties="springBootApp.postConstructOff=true")
@AutoConfigureMockMvc
public class EventListServiceTest {

    @Autowired
    private EventListService eventListService;

    @Autowired
    private EventListRepository eventListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mongo mongo;

    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Before
    public void before() {
        mongo.dropDatabase(mongoDbFactory.getDb().getName());
    }

    @Test
    public void shouldCreateEventList() {
        eventListService.createEventList(new EventList("eventList1"));
        assertThat(eventListRepository.exists(Example.of(new EventList("eventList1"))), equalTo(true));
    }

    @Test
    public void shouldUpdateEventList() throws CustomException {
        User user = new User("test", "test");
        user = userRepository.save(user);

        EventList eventList = new EventList("a");
        eventList.setCreationDate(new Date());
        eventList.setUserId(user.getId());
        eventList = eventListRepository.save(eventList);
        EventList newEventList = new EventList("bb");

        eventListService.updateEventList(eventList.getId(), newEventList, user);
        assertThat(eventListRepository.exists(Example.of(newEventList)), equalTo(true));
    }

    @Test(expected = EventListNotFoundException.class)
    public void shouldFailUpdateEventListBecauseNotExist() throws CustomException {
        eventListService.updateEventList("a", new EventList("1"), null);
    }

    @Test
    public void shouldDeleteEventList() throws CustomException {
        User user = new User("test", "test");
        user = userRepository.save(user);
        EventList eventList = new EventList("a");
        eventList.setCreationDate(new Date());
        eventList.setUserId(user.getId());
        eventList = eventListRepository.save(eventList);
        eventListService.delete(eventList.getId(), user);
        assertThat(eventListRepository.exists(Example.of(eventList)), equalTo(false));
    }

    @Test
    public void shouldAddEventToEventList() throws CustomException{
        User user = new User("test", "test");
        user = userRepository.save(user);
        EventList eventList = new EventList("a");
        eventList.setCreationDate(new Date());
        eventList.setUserId(user.getId());
        eventList = eventListRepository.save(eventList);

        eventListService.addEvent(eventList.getId(), 1L, user);
        eventListService.addEvent(eventList.getId(), 2L, user);

        eventList = eventListService.findById(eventList.getId());

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

        Page<EventList> result = eventListService.searchPaginated("", 1 , 1, null);
        assertThat(result.getTotalPages(), equalTo(2));
        assertThat(result.getTotalElements(), equalTo(2L));
    }

    @Test
    public void shouldCountUserInterested() {
        assertThat(eventListService.usersInterested(1L), equalTo(0));
    }


}
