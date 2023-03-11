package ru.itis.stockmarket.services;

import ru.itis.stockmarket.dtos.BankRequestDto;
import ru.itis.stockmarket.dtos.BankResponseDto;

import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA
 * Date: 14.05.2022
 * Time: 1:14 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public interface OrganizationService<RequestDto, ResponseDto> {
    ResponseDto createOrganization(RequestDto organizationDto);

    ResponseDto updateOrganization(UUID id, RequestDto partialOrganizationDto);

    ResponseDto getOrganizationWithId(UUID id);

    List<ResponseDto> getAllOrganizations();

    List<ResponseDto> getOrganizationsFrom(String countryCode);

    UUID deleteOrganizationWithId(UUID id);

}
