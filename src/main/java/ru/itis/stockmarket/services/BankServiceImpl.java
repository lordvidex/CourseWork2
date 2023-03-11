package ru.itis.stockmarket.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.stockmarket.dtos.BankRequestDto;
import ru.itis.stockmarket.dtos.BankResponseDto;
import ru.itis.stockmarket.exceptions.AlreadyExistsException;
import ru.itis.stockmarket.exceptions.NotFoundException;
import ru.itis.stockmarket.mappers.BankMapper;
import ru.itis.stockmarket.models.Account;
import ru.itis.stockmarket.models.Bank;
import ru.itis.stockmarket.models.Country;
import ru.itis.stockmarket.repositories.AccountRepository;
import ru.itis.stockmarket.repositories.BankRepository;
import ru.itis.stockmarket.repositories.CountryRepository;

import javax.transaction.Transactional;
import java.util.*;

import static ru.itis.stockmarket.dtos.BankResponseDto.from;

/**
 * Created by IntelliJ IDEA
 * Date: 29.04.2022
 * Time: 10:02 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final BankMapper mapper;
    private final CountryRepository countryRepository;
    private final AccountRepository accountRepository;

    @Override
    public BankResponseDto createOrganization(BankRequestDto bankDto) {
        // get country instance or create if it doesn't exist
        Country country = this.findCountryByCodeOrCreate(bankDto.getCountry());

        // check if a bank already exist for this country
        boolean bankAlreadyExists = this.bankRepository.existsBankByCountry(country);
        if (bankAlreadyExists) {
            throw new AlreadyExistsException(String.format("National Bank from %s already exists", country.getCode()));
        }

        // build bank
        Bank bank = Bank.builder()
                .name(bankDto.getName())
                .address(bankDto.getAddress())
                .country(country)
                .build();
        // by default add 100 million to each bank accounts
        addDefaultBankAccounts(bank);
        return from(bank);
    }

    @Override
    public BankResponseDto updateOrganization(UUID id, BankRequestDto bankDto) {
        Bank fetchedBank = _getBankWithId(id);
        this.mapper.updateBankFromDto(bankDto, fetchedBank);
        if (fetchedBank.getCountry() != null) {
            this.countryRepository.save(fetchedBank.getCountry());
        }
        // save
        return from(this.bankRepository.save(fetchedBank));
    }

    @Override
    public BankResponseDto getOrganizationWithId(UUID id) {
        return from(_getBankWithId(id));
    }

    private Bank _getBankWithId(UUID id) {
        return this.bankRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Bank with id %s not found", id)));
    }

    @Override
    public List<BankResponseDto> getAllOrganizations() {
        return from(this.bankRepository.findAll());
    }

    @Override
    public List<BankResponseDto> getOrganizationsFrom(String countryCode) {
        ArrayList<Bank> banks = new ArrayList<>();
        this.bankRepository.findBankByCountry_Code(countryCode).ifPresent(banks::add);
        return from(banks);
    }

    @Override
    public UUID deleteOrganizationWithId(UUID id) {
        _getBankWithId(id); // throws if bank does not exist
        try {
            this.bankRepository.deleteById(id);
            return id;
        } catch (Exception e) {
            return null;
        }
    }

    private Country findCountryByCodeOrCreate(String countryCode) {
        return this.countryRepository
                .findCountryByCode(countryCode)
                .or(() -> {
                    Country c = new Country();
                    c.setCode(countryCode); // lombok cannot set from superClass
                    this.countryRepository.save(c);
                    return Optional.of(c);
                }).get();
    }


    private void addDefaultBankAccounts(Bank bank) {
        // add my country account to other banks and add their account to my bank
        List<Bank> oldBanks = this.bankRepository.findAll();
        this.bankRepository.save(bank); // save bank first else there will be an error
        for (Bank eachOldBank: oldBanks) {
            Account newBankAccount = makeNewAccount(eachOldBank,bank.getCountry());
            Account oldBankAccount = makeNewAccount(bank,eachOldBank.getCountry());

            this.accountRepository.save(oldBankAccount);
            this.accountRepository.save(newBankAccount);
        }
        // add my own account
        Account myAccount = makeNewAccount(bank, bank.getCountry());
        this.accountRepository.save(myAccount);
    }

    /**
     * Builds an account object with a default of 100 million
     * @param bank account owner
     * @param country currency country
     * @return new Account for this bank with the account of country
     */
    private Account makeNewAccount(Bank bank, Country country) {
        Optional<Account> optionalAccount = this.accountRepository.findByBankAndCountry(bank,country);
        return optionalAccount.orElseGet(() -> Account.builder()
                .country(country)
                .bank(bank)
                .balance(100_000_000)
                .build());
    }
}
