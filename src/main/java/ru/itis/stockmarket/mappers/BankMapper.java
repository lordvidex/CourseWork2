package ru.itis.stockmarket.mappers;

import org.mapstruct.*;
import ru.itis.stockmarket.dtos.BankRequestDto;
import ru.itis.stockmarket.models.Bank;

/**
 * Created by IntelliJ IDEA
 * Date: 30.04.2022
 * Time: 8:47 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@Mapper(componentModel = "spring")
public interface BankMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "country.code", source = "country")
    void updateBankFromDto(BankRequestDto dto, @MappingTarget Bank entity);
}
