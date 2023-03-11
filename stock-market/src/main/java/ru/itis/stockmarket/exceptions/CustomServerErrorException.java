package ru.itis.stockmarket.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Created by IntelliJ IDEA
 * Date: 17.05.2022
 * Time: 8:13 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class CustomServerErrorException extends HttpServerErrorException {
    public CustomServerErrorException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
