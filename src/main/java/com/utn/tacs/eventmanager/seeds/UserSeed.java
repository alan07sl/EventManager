package com.utn.tacs.eventmanager.seeds;

import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.repositories.UserRepository;
import com.utn.tacs.eventmanager.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Slf4j
class UserSeed {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user1 = new User("test1", encoder.encode("test1"));
        user1.setIsAdmin(true);

        return args -> {
            log.info("Preloading " + repository.save(user1));
            log.info("Preloading " + repository.save(new User("test2", encoder.encode("test2"))));
        };
    }
}