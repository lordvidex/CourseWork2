package ru.itis.dis.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

    @PostMapping
    public Cars createCar(@RequestBody Cars car) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.persist(car);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{id}")
    public Cars getCarWithId(@PathVariable String id) throws Exception {
       EntityManager em = entityManagerFactory.createEntityManager();
        Cars result = em.find(Cars.class,id);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with id "+id+" not found");
        } else {
            return result;
        }
    }

    @DeleteMapping("/{id}")
    public Cars deleteCarWithId(@PathVariable String id) throws Exception {
        EntityManager em = entityManagerFactory.createEntityManager();
        Cars toBeDeleted = getCarWithId(id);
        em.remove(Cars.class,id);
        return toBeDeleted;
    }

    @PutMapping("/{id}")
    public Cars updateCarWithid(@PathVariable String id, @RequestBody Cars newCar) throws Exception {
        EntityManager em = entityManagerFactory.createEntityManager();
        newCar.setId(Integer.parseInt(id));
        return em.merge(newCar);
    }
}
