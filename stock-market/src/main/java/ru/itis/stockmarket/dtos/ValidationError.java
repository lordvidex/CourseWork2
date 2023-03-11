package ru.itis.stockmarket.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * Date: 14.05.2022
 * Time: 11:57 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationError extends GeneralMessage<String> {
    private Long timestamp;
    private int statusCode;
    private String path;
    private Map<String,String> errors;
}
