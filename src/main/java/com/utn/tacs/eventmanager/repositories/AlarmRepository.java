package com.utn.tacs.eventmanager.repositories;

import com.utn.tacs.eventmanager.dao.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlarmRepository extends JpaRepository<Alarm,Long> {

}
