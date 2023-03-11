package ru.itis.stockmarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.stockmarket.models.Account;
import ru.itis.stockmarket.models.Bank;
import ru.itis.stockmarket.models.Country;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA
 * Date: 27.05.2022
 * Time: 12:27 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByBankAndCountry(Bank bank, Country currency);

}
