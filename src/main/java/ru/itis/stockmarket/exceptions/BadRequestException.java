package ru.itis.stockmarket.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by IntelliJ IDEA
 * Date: 23.05.2022
 * Time: 1:08 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class BadRequestException extends CustomServerErrorException {
    public BadRequestException(String statusText) {
        super(HttpStatus.BAD_REQUEST, statusText);
    }
}
