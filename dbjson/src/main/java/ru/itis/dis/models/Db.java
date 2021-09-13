package ru.itis.dis.models;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Date: 13.09.2021
 * Time: 9:48 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Db {
    public List<Client> clients;
    public List<Car> cars;
    public List<Driver> drivers;
    public List<Ride> rides;

    public Db(List<Client> clients, List<Car> cars, List<Driver> drivers, List<Ride> rides) {
        this.clients = clients;
        this.cars = cars;
        this.rides = rides;
        this.drivers = drivers;
    }
    public Db(){}
}
