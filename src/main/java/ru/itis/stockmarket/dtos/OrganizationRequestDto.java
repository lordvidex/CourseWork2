package ru.itis.stockmarket.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import ru.itis.stockmarket.converters.ToLowerCaseConverter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 * Created by IntelliJ IDEA
 * Date: 14.05.2022
 * Time: 1:16 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Data
public class OrganizationRequestDto {
    @NotEmpty(groups = {Default.class, OnCreate.class})
    private String name;

    @NotEmpty(groups = {Default.class,OnCreate.class})
    private String address;

    @NotEmpty(groups = {Default.class,OnCreate.class})
    @Size(min = 2, max = 2, message = "Length of country should be 2, country code as specified by ISO-3166-1")
    @JsonDeserialize(converter = ToLowerCaseConverter.class)
    private String country;
}

