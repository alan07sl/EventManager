package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.errors.UserExistException;
import com.utn.tacs.eventmanager.errors.UserNotFoundException;
import com.utn.tacs.eventmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void createUser(User user) throws CustomException {
        if (userRepository.exists(Example.of(new User(user.getUsername(), null)))) {
            throw new UserExistException(user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public Page<User> searchPaginated(User user, int page, int size) {
        return userRepository.findAll(Example.of(user), new PageRequest(page - 1, size));
    }

    public User findById(Integer id) throws CustomException {
        return userRepository.findById(id.longValue()).orElseThrow(() -> new UserNotFoundException());
    }
}
