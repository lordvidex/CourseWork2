package ru.itis.stockmarket.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.itis.stockmarket.dtos.GeneralMessage;
import ru.itis.stockmarket.dtos.Status;
import ru.itis.stockmarket.models.Bank;
import ru.itis.stockmarket.services.BankService;

/**
 * Created by IntelliJ IDEA
 * Date: 27.04.2022
 * Time: 10:51 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */


@RestController
@RequestMapping(path = "/bank")
public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping()
    Object createBank() {
       Bank bank = this.bankService.createBank();
       if (bank == null) {
           return GeneralMessage.builder().status(Status.failure).description("Failed to create new bank");
       } else {
           return bank;
       }
    }
}
