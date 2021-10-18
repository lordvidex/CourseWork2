-- 2) AVE, MAX, MIN, SUM
select '1-7 june 2017'                as date_range,
       coalesce(avg(total_amount), 0) as avg,
       coalesce(max(total_amount), 0) as max,
       coalesce(min(total_amount), 0) as min,
       coalesce(sum(total_amount), 0) as sum
from bookings
where book_date between '2017-06-01' and '2017-06-07'
UNION
select '8) — 15) июль 2017' as date_range,
       coalesce(avg(total_amount), 0),
       coalesce(max(total_amount), 0),
       coalesce(min(total_amount), 0),
       coalesce(sum(total_amount), 0)
from bookings
where book_date between '2017-07-08' and '2017-07-15'
UNION
select '16) — 22) август 2017' as date_range,
       coalesce(avg(total_amount), 0),
       coalesce(max(total_amount), 0),
       coalesce(min(total_amount), 0),
       coalesce(sum(total_amount), 0)
from bookings
where book_date between '2017-07-16' and '2017-07-22';

-- 3
select tickets.passenger_name, temp.total_amount
from tickets
         join (
    select *
    from (select bookings.*
          from bookings
          where book_date between '2017-07-10' and '2017-07-11') as thisDate
             join (select min(total_amount)
                   from (select bookings.*
                         from bookings
                         where book_date between '2017-07-10' and '2017-07-11') as thisDate) as minx
                  on minx.min = thisDate.total_amount) as temp
              on temp.book_ref = tickets.book_ref;


-- 4 [CREATE FUNCTION]
drop function if exists getDistance(p1 point, p2 point);
create function getDistance(p1 point, p2 point) returns float
    language plpgsql as
$$
declare
    radLatP1 float;
    radLatP2 float;
    theta    float;
    radTheta float;
    angle    float;
    result   float = 0;
begin
    radLatP1 = pi() * p1[1] / 180;
    radLatP2 = pi() * p2[1] / 180;
    theta = p1[0] - p2[0];
    radTheta = pi() * theta / 180;
    angle = sin(radLatP1) * sin(radLatP2) + cos(radLatP1) * cos(radLatP2) * cos(radTheta);
    IF angle > 1 THEN angle = 1; END IF;

    result = acos(angle) * 180 / pi();
    result = result * 60 * 1.1515 * 1.609344;
    return result;
end;
$$;
-- 4 [USE DISTANCE FUNCTION]
select getDistance((select coordinates from airports_data where airport_name::json ->> 'ru' = 'Сургут'),
                   (select coordinates from airports_data where airport_name::json ->> 'ru' = 'Псков')
           );


-- 5
DROP DATABASE IF EXISTS farm;
create database farm;
\c farm;
create table veterinary
(
    id         serial      not null primary key,
    name       varchar(50) not null,
    experience integer     not null check (experience > 2)
);
-- Конюхи
create table groom
(
    id   serial not null primary key,
    name varchar(70),
    age  int    not null check ( age < 51 )
);
-- many to many stable/groom
create table groom_stable
(
    groom_id      integer not null references groom (id),
    stable_number integer not null references stable (number)
);

-- Конюшня
create table stable
(
    number        serial  not null primary key,
    stall_number  integer check (stall_number > 9 and stall_number < 51),
    veterinary_id integer not null references veterinary (id)
);
create table animals
(
    type          varchar(50),
    breed         varchar(50),
    gender        varchar(10) not null,
    age           numeric     not null,
    stable_number integer references stable (number)
);

-- 6
select passenger_id, passenger_name
from tickets
         join
     (select distinct(ticket_no) as ticket_no
      from (select *
            from flights
            where arrival_airport = 'LED'
              and scheduled_departure between '2017-08-05' and '2017-08-06') as pulkovo_timed_flights
               join seats on pulkovo_timed_flights.aircraft_code = seats.aircraft_code
               join ticket_flights on pulkovo_timed_flights.flight_id = ticket_flights.flight_id
      where seats.fare_conditions = 'Economy') as ticket_number
     on ticket_number.ticket_no = tickets.ticket_no;

-- 7 (нет результата [работает от '2017-07-15'])
select scheduled_departure,
       flight_no,
       departure_airport,
       arrival_airport,
       person.passenger_name
from flights
         join
     (select *
      from tickets
               join ticket_flights tf on tickets.ticket_no = tf.ticket_no) as person
     on person.flight_id = flights.flight_id
where scheduled_departure between '2017-07-05' and '2017-07-06';
--
-- select scheduled_departure,
--        flight_no,
--        departure_airport,
--        arrival_airport,
--        person.passenger_name from flights
--                                       join
--                                   (select * from tickets join ticket_flights tf on tickets.ticket_no = tf.ticket_no) as person
--                                   on person.flight_id = flights.flight_id
-- where scheduled_departure between '2017-07-15' and '2017-07-16';