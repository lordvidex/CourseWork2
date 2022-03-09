package ru.itis.entities;

import ru.itis.annotations.Column;
import ru.itis.annotations.Entity;
import ru.itis.annotations.OneToMany;
import ru.itis.annotations.PrimaryColumn;

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
@Entity
public class Drivers {
    @PrimaryColumn
    int id;
    @Column
    String name;
    @Column
    String phone;
    @OneToMany
    Cars car;
}
