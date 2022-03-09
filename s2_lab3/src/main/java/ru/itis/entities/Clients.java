package ru.itis.entities;

import ru.itis.annotations.Column;
import ru.itis.annotations.Entity;
import ru.itis.annotations.PrimaryColumn;

/**
 * Created by IntelliJ IDEA
 * Date: 09.03.2022
 * Time: 4:00 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Entity
public class Clients {
    @PrimaryColumn
    int id;
    @Column
    String phone;
    @Column
    String name;
}
