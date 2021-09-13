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
public class Client {
     public int clientId;
     public String phone;
    public String name;

    public Client(int clientId,String phone, String name) {
        this.clientId = clientId;
        this.phone = phone;
        this.name = name;
    }
    public Client() {}
}
