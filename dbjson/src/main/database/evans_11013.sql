-- clean up
DROP DATABASE IF EXISTS uberdb;
DROP TABLE IF EXISTS clients,cars,drivers,rides;

-- create the database
CREATE DATABASE uberdb;
\connect uberdb;

-- create the tables

-- clients table
CREATE TABLE clients (
    id INT NOT NULL PRIMARY KEY,
    phone VARCHAR(20),
    name VARCHAR(50)
);

-- cars table
CREATE TABLE cars (
    id INT NOT NULL PRIMARY KEY,
    model VARCHAR(30),
    number VARCHAR(20)
);

-- drivers table
CREATE TABLE drivers (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    car INT NOT NULL REFERENCES cars(id)
);

-- rides table
CREATE TABLE rides (
  client_id INT NOT NULL REFERENCES clients(id),
  driver_id INT NOT NULL REFERENCES drivers(id),
  amount NUMERIC NOT NULL,
  time TIMESTAMP WITH TIME ZONE NOT NULL,
  completed BOOLEAN DEFAULT false
);

-- insert clients
INSERT INTO clients (id, phone, name) VALUES
                                             (1,'11123','client1'),
                                             (2,'23543','client2');

-- insert cars
INSERT INTO cars (id, model, number) VALUES
                                            (1,'35432ERE','Benz'),
                                            (2,'FEDW4322','Ford');

-- insert drivers
INSERT INTO drivers (id, name, phone, car) VALUES
                                                  (3,'Driver1','32342',1),
                                                  (4,'Driver2','23412',2);

-- insert rides
INSERT INTO rides (client_id, driver_id, amount, time, completed) VALUES (1,3,50,TIMESTAMP '2021-09-12 05:00:00+03', true),
                                                                         (2,4,100,TIMESTAMP '2021-09-12 05:10:00+03', true),
                                                                         (1,4,96,TIMESTAMP '2021-09-13 05:20:00+03',false),
                                                                         (2,3,200,TIMESTAMP '2021-09-13 05:09:00+03', false);

-- test section
SELECT * FROM rides;




