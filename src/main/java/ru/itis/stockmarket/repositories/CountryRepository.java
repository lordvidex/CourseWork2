package ru.itis.stockmarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.stockmarket.models.Country;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA
 * Date: 30.04.2022
 * Time: 6:02 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findCountryByCode(String code);
}
