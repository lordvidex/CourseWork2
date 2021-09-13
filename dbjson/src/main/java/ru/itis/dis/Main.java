package ru.itis.dis;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.dis.models.*;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA
 * Date: 13.09.2021
 * Time: 9:52 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Main {
    public static void main(String[] args) {

        // Task 1,2,3
        Db db = createCustomDb();

        ObjectMapper mapper = new ObjectMapper();

        File jsonFile = null;
        try {
            jsonFile = new File("dbjson/src/main/resources/db.json");
            mapper.writeValue(jsonFile, db);
        } catch (IOException e){
           System.err.println(e);
            System.out.println("Failed to read file");
        }

        // Task 4
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
            mapper.setDateFormat(df);
            Db fetchedDb = mapper.readValue(jsonFile, Db.class);
            /*  CLIENT ID TO SEARCH FOR */
            int clientId = 1;
            searchForClientRides(fetchedDb, clientId);
        } catch (IOException e) {
            System.err.println(e);
            System.out.println("Failed to read json file");
        }

    }

    private static Db createCustomDb() {
        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<Car> cars = new ArrayList<>();
        ArrayList<Driver> drivers = new ArrayList<>();
        Client client1 = new Client(1,"11123","client1");
        Client client2 = new Client(2,"23543","client2");

        clients.add(client1); clients.add(client2);

        Car car1 = new Car("35432ERE", "Benz");
        Car car2 = new Car("FEDW4322","Ford");

        cars.add(car1); cars.add(car2);

        Driver driver = new Driver(3,"32342","Driver1",car1);
        Driver driver2 = new Driver(4,"23412", "Driver2", car2);

        drivers.add(driver); drivers.add(driver2);
//new Date()
        Ride ride1 = new Ride(1,3,50, true, new Date(2021,Calendar.SEPTEMBER,12,8,0));
        Ride ride2 = new Ride(2,4,100,true,new Date(2021,Calendar.SEPTEMBER,12,8,10));
        Ride ride3 = new Ride(1,4,96,new Date(2021, Calendar.SEPTEMBER,13,8,20));
        Ride ride4 = new Ride(2,3,200,new Date(2021,Calendar.SEPTEMBER,13,8,9));
        ArrayList<Ride> rides = new ArrayList<>(List.of(ride1, ride2, ride3, ride4));

        return new Db(clients, cars, drivers,rides);
    }

    private static void searchForClientRides(Db db, int clientId) {
        System.out.println("Rides for Client "+clientId);
        List<Ride> rides = db.rides.stream()
                .filter(ride -> ride.clientId == clientId)
                .collect(Collectors.toList());
        for (Ride r: rides) {
            System.out.println("Date: "+r.date+", Car: "+ getCarFromDriverId(db.drivers,r.driverId));
        }

    }

    private static Car getCarFromDriverId(List<Driver> drivers, int driverId) {
        Optional<Driver> optionalDriver = drivers.stream().filter(driver -> driver.id == driverId).findFirst();
        if(optionalDriver.isEmpty()) return null;
        else {
            return optionalDriver.get().car;
        }
    }
}
