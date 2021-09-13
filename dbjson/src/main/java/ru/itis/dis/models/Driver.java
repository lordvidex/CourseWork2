package ru.itis.dis.models;

/**
 * Created by IntelliJ IDEA
 * Date: 13.09.2021
 * Time: 9:49 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Driver {
    public int id;
    public String phone;
    public String name;
    public Car car;

    public Driver(int id,String phone, String name, Car car) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.car = car;
    }
    public Driver() {}
}
