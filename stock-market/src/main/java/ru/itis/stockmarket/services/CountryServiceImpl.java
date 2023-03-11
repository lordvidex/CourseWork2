package ru.itis.stockmarket.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.stockmarket.models.Country;
import ru.itis.stockmarket.repositories.CountryRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA
 * Date: 30.05.2022
 * Time: 8:27 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@RequiredArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    @Override
    public List<String> getAllCountryCodes() {
        return this.countryRepository.findAll()
                .stream().map(Country::getCode)
                .collect(Collectors.toList());
    }
}
