package com.utn.tacs.eventmanager.repositories;

import com.utn.tacs.eventmanager.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
