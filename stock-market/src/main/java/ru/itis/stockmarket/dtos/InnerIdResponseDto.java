package ru.itis.stockmarket.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA
 * Date: 14.05.2022
 * Time: 12:22 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
// */
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class InnerIdResponseDto extends GeneralMessage<UUID> {
    private UUID innerid;
}
