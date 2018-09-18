package com.utn.tacs.eventmanager.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Alarm {
    private @Id @GeneratedValue Long id;
    private @ManyToOne User user;
}
