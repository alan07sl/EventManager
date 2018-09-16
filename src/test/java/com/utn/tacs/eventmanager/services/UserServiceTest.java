package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.errors.UserExistException;
import com.utn.tacs.eventmanager.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldCreateUser() throws CustomException {
        userService.createUser(new User("martin","1234"));
        assertThat(userRepository.exists(Example.of(new User("martin", null))), equalTo(true));
    }

    @Test
    public void shouldFailCreateUserBecauseAlreadyExist() throws CustomException {
        User user = new User("martin","1234");
        userService.createUser(user);
        try{
            userService.createUser(user);
            assertThat("Should fail because user already exist", false);
        } catch (UserExistException e) {
            assertThat("User already exist", true);
        }
    }

    @Test
    public void shouldNotFailCreateDifferentUsers() throws CustomException {
        User user = new User("martin","1234");
        User user2 = new User("alan","1234");
        userService.createUser(user);
        try{
            userService.createUser(user2);
            assertThat("Should not fail because user already exist", true);
        } catch (UserExistException e) {
            assertThat("User already exist", false);
        }
    }
}
