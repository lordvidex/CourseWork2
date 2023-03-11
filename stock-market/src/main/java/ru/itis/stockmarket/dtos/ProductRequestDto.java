package ru.itis.stockmarket.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.UUID;


@Data
public class ProductRequestDto {
    @NotEmpty(groups = {Default.class, OnCreate.class})
    private String code;
    @NotEmpty(groups = {Default.class, OnCreate.class})
    private String name;
    @JsonProperty(value = "sellerid")
    @NotNull(groups = {Default.class, OnCreate.class})
    private UUID sellerId;
    @DecimalMin(value = "1.0" ,groups = {Default.class, OnCreate.class})
    private double count;
    @NotNull(groups = {Default.class, OnCreate.class})
    private Long unit;
    @DecimalMin(value = "1.0" , groups = {Default.class, OnCreate.class})
    private double price;
}
