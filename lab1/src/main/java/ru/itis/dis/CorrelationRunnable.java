package ru.itis.dis;

import ru.itis.dis.Models.CorrelationResult;
import ru.itis.dis.Models.DataTicket;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA
 * Date: 01.09.2021
 * Time: 3:31 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class CorrelationRunnable implements Runnable {
    private final List<DataTicket> data;
    private final DataGetter transform;
    private final CorrelationResult result;
    public CorrelationRunnable(List<DataTicket> data,
                               DataGetter transform,
                               CorrelationResult result) {
        this.data = data;
        this.transform = transform;
        this.result = result;
    }

    @Override
    public void run() {
        assert(data!=null && transform!=null);
        calcStandardDeviation();
    }

    private void calcStandardDeviation() {
        List<Double> specificData = data.stream()
                .map(transform::getData)
                .collect(Collectors.toList());
        // get the mean
        double dataMean = mean(specificData);

        List<Double> corrDifference = data.stream().map(dataTicket -> {
//            double diff = transform.getData(dataTicket) - dataMean;
//            return diff * diff;
            return transform.getData(dataTicket) * transform.getData(dataTicket)
                    - dataMean * dataMean;
        }).collect(Collectors.toList());

        double variance = mean(corrDifference);
        result.setStandardDeviation(Math.sqrt(variance));
        result.setMean(dataMean);
    }

    private double mean(List<Double> data) {
        int n = data.size();
        double sum = 0;
        for(Double each: data) {
            sum+=each;
        }
        return sum/n;
    }
}
