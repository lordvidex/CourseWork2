package ru.itis.dis.Models;

/**
 * Created by IntelliJ IDEA
 * Date: 01.09.2021
 * Time: 10:09 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class CorrelationResult {
    private double standardDeviation;

    private double mean;

    public CorrelationResult() {}

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public double getMean() {
        return mean;
    }


    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }
}
