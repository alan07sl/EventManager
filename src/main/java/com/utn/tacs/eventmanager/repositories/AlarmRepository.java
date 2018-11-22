package com.utn.tacs.eventmanager.repositories;

import com.utn.tacs.eventmanager.dao.Alarm;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface AlarmRepository extends MongoRepository<Alarm,String> {

    public List<Alarm> findByUserId(String userId);
}
