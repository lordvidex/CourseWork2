package ru.itis.dis.Models;

/**
 * Created by IntelliJ IDEA
 * Date: 01.09.2021
 * Time: 11:04 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

public class DataTicket implements Comparable<DataTicket> {
    private final Long date;
    private final Double closeX;
    private final Double closeY;

    /**
     * Maps the same date to close price from the first file and the second file
     * @param date unique date, time where the prices were taken
     * @param closeX price from the first file
     * @param closeY price from the second file
     */
    public DataTicket(Long date, Double closeX, Double closeY) {
        this.date = date;
        this.closeX = closeX;
        this.closeY = closeY;
    }

    public Double getCloseX() {
        return closeX;
    }

    public Double getCloseY() {
        return closeY;
    }

    @Override
    public int compareTo(DataTicket o) {
        return (int) (date - o.date);
    }
}
