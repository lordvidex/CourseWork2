package ru.itis.dis.models;

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
public class Car {
    public String number;
    public String model;

    public Car(String number, String model) {
        this.number = number;
        this.model = model;
    }
    public Car() {}

    @Override
    public String toString() {
        return "Car{" +
                "number='" + number + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
