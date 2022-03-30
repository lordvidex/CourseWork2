package ru.itis.dis.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itis.dis.managers.EntityManager;
import ru.itis.dis.managers.EntityManagerFactory;
import ru.itis.dis.entities.Cars;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Date: 30.03.2022
 * Time: 12:33 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@RestController
@RequestMapping(path = "/cars")
public class CarsController {
    private final EntityManagerFactory entityManagerFactory;

    public CarsController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @GetMapping("/{id}")
    public Cars getCarWithId(@PathVariable String id) throws Exception {
       EntityManager em = entityManagerFactory.createEntityManager();
        return em.find(Cars.class,id);
    }

    @DeleteMapping("/{id}")
    public Object deleteCarWithId(@PathVariable String id) throws Exception {
        return new HashMap<>(){{
            put("deleted", true);
        }};
    }
}
