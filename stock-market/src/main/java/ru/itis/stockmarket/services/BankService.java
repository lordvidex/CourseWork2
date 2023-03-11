package ru.itis.stockmarket.services;

import org.springframework.stereotype.Service;
import ru.itis.stockmarket.models.Bank;
import ru.itis.stockmarket.repositories.BankRepository;

/**
 * Created by IntelliJ IDEA
 * Date: 27.04.2022
 * Time: 10:59 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank createBank() {
        return this.bankRepository.save(new Bank());
    }
}
