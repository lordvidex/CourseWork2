package ru.itis.dis.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA
 * Date: 09.03.2022
 * Time: 3:56 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@Table(name = "cars")
@Entity
@Data
public class Cars {
    @Column(name = "id")
    @Id
    int id;

    @Column(name = "model")
    String model;

    @Column(name = "number")
    String number;
}
