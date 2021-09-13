package ru.itis.dis.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by IntelliJ IDEA
 * Date: 13.09.2021
 * Time: 10:53 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Ride {
    public int clientId;
    public int driverId;
    public int amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date date;
    public boolean completed = false;

    public Ride(int clientId, int driverId, int amount,boolean completed, Date date) {
        this(clientId, driverId, amount,date);
        this.completed = completed;
    }

    public Ride(int clientId, int driverId, int amount, Date date) {
        this.clientId = clientId;
        this.date = date;
        this.driverId = driverId;
        this.amount = amount;
    }
    public Ride() {}
}
