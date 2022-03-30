package ru.itis.dis.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "clients")
@Entity
@Data
public class Clients {
    @Id
    @Column(name = "id")
    int id;

    @Column(name = "phone")
    String phone;

    @Column(name = "name")
    String name;
}
