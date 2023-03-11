package ru.itis.stockmarket.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

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
@SuperBuilder(toBuilder = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralMessage<T> {
    @Builder.Default
    private Status status = Status.success;
    @Builder.Default
    private String description = "OK";
    @Nullable private T data;
}
