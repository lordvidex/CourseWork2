package ru.itis.stockmarket.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA
 * Date: 27.04.2022
 * Time: 10:54 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GeneralMessage {
    private Status status;
    private String description;
    private String data;
}
