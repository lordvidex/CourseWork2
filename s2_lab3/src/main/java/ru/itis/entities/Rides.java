package ru.itis.entities;

import ru.itis.annotations.Column;
import ru.itis.annotations.Entity;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA
 * Date: 09.03.2022
 * Time: 4:01 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Entity
public class Rides {
    @Column
    int clientId;
    @Column
    int driverId;
    @Column
    double amount;
    @Column
    LocalDateTime time;
    @Column
    boolean completed;
    @Column
    String tripTo;
}
