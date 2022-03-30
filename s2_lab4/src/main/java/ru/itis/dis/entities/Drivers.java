package ru.itis.dis.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Date: 09.03.2022
 * Time: 3:57 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Table(name = "drivers")
@Entity
@Data
public class Drivers {
    @Column
    @Id
    int id;
    @Column(name = "name")
    String name;

    @Column(name = "phone")
    String phone;

//    @OneToMany(targetEntity = Cars.class)
    @Column(name = "car")
    int car;
}
