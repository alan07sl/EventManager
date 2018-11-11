package com.utn.tacs.eventmanager.services;

import com.mongodb.Mongo;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.errors.InvalidCredentialsException;
import com.utn.tacs.eventmanager.errors.UserExistException;
import com.utn.tacs.eventmanager.errors.UserNotFoundException;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(properties="springBootApp.postConstructOff=true")
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private UserService userService;

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
    public void shouldSuccessSearchPaginatedOfUsers() throws CustomException {
        userRepository.deleteAll();
        User u1 = new User("a", "123");
        User u2 = new User("b", "123");

        userRepository.saveAll(Arrays.asList(u1, u2));
        Page<User> result = userService.searchPaginated(new User(null,null), 1 , 1);
        assertThat(result.getTotalPages(), equalTo(2));
        assertThat(result.getTotalElements(), equalTo(2L));
    }


    @Test
    public void shouldCreateUser() throws CustomException {
        userService.createUser(new User("martin", "1234"));
        assertThat(userRepository.exists(Example.of(new User("martin", null))), equalTo(true));
    }

    @Test(expected = UserExistException.class)
    public void shouldFailCreateUserBecauseAlreadyExist() throws CustomException {
        User user = new User("martin", "1234");
        userService.createUser(user);
        userService.createUser(user);
    }

    @Test
    public void shouldAuthenticateUser() throws CustomException {
        User user = new User("martin", "1234");
        userService.createUser(user);
        User authenticateUser = userService.authenticateUser("martin", "1234");
        assertThat(authenticateUser.getUsername(), equalTo(user.getUsername()));
    }

    @Test
    public void shouldAuthenticateAdminUser() throws CustomException {
        User user = new User("martin", "1234");
        user.setIsAdmin(true);
        userService.createUser(user);
        User authenticateUser = userService.authenticateUser("martin", "1234");
        assertThat(authenticateUser.getUsername(), equalTo(user.getUsername()));
    }

    @Test(expected = InvalidCredentialsException.class)
    public void shouldFailOnInvalidEmail() throws CustomException {
        User user = new User("martin4", "1234");
        userService.createUser(user);
        userService.authenticateUser("alan", "1234");
    }

    @Test(expected = InvalidCredentialsException.class)
    public void shouldFailOnInvalidPassword() throws CustomException {
        User user = new User("martin2", "1234");
        userService.createUser(user);
        userService.authenticateUser("martin2", "asdasd");
    }

    @Test
    public void shouldSuccessGetOfUser() throws CustomException {
        User user = new User("a", "123");

        userRepository.save(user);

        User foundUser = userService.findById(user.getId());
        assertThat(user.getUsername(), equalTo(foundUser.getUsername()));
        assertThat(user.getPassword(), equalTo(foundUser.getPassword()));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldFailGetOfUserBecauseNotExist() throws CustomException {
        User user = new User("a", "123");

        userRepository.save(user);
        userService.findById(user.getId() + "22");

    }
}
