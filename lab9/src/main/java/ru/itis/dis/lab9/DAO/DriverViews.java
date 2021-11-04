package ru.itis.dis.lab9.DAO;

/**
 * Created by IntelliJ IDEA
 * Date: 04.11.2021
 * Time: 3:33 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class DriverViews {
    private int id;
    private String name;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    private int carId;

    public DriverViews(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public DriverViews(int id, String name, String phone, int carId) {
        this(id, name, phone);
        this.carId = carId;
    }
}
