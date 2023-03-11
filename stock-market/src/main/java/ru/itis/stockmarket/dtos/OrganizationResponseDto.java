package ru.itis.stockmarket.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA
 * Date: 14.05.2022
 * Time: 1:24 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationResponseDto {
    private String name;
    @JsonProperty(value = "innerid")
    private UUID innerId;
    private String address;
    @JsonProperty(value = "country_code")
    private String countryCode;
    private Collection<UUID> products;
}
