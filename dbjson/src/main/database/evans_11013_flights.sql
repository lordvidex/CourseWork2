-- task 1: Найти полеты (flights) из аэропорта Казани в Магнитогорск
SELECT *
FROM flights
WHERE departure_airport
    = (SELECT airport_code FROM airports_data WHERE city::json ->> 'en' = 'Kazan')
  AND arrival_airport
    = (SELECT airport_code FROM airports_data WHERE city::json ->> 'ru' = 'Магнитогорск');



-- task 2: Найти все полеты иx Москвы (все аэропорты) за 25 минут (за какую дату и час придумайте сами)

-- 1. set the time we want to get from
-- 2. get moscow airports
-- 3. (INNER) join the moscow airports table with the flights table
-- 4. filter the time

WITH mytime AS (VALUES (TIMESTAMP '2017-07-16 06:00:00.000000'))
SELECT flights.*
FROM flights
         JOIN (SELECT * FROM airports_data WHERE city::JSON ->> 'en' = 'Moscow') AS moscow
              ON flights.departure_airport = moscow.airport_code
WHERE scheduled_departure BETWEEN (SELECT * FROM mytime)
  AND ((SELECT * FROM mytime) + '25 minutes')
ORDER BY scheduled_departure;



-- task 3:
-- 1. select all tables in flights table
-- 2. convert the timestamps tables to Moscow time
-- 3. join with airports table where departure airport = airport_code

SELECT flight_id,
       flight_no,
       scheduled_departure AT TIME ZONE 'Europe/Moscow' AS scheduled_departure_Moscow,
       scheduled_arrival AT TIME ZONE 'Europe/Moscow'   as scheduled_arrival_Moscow,
       arrival_airport,
       status,
       aircraft_code,
       actual_departure AT TIME ZONE 'Europe/Moscow'    AS actual_departure_Moscow,
       actual_arrival AT TIME ZONE 'Europe/Moscow'      AS actual_arrival_Moscow
FROM flights
         LEFT OUTER JOIN airports_data ON flights.departure_airport = airports_data.airport_code;

