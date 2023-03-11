package ru.itis.stockmarket.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

/**
 * Created by IntelliJ IDEA
 * Date: 29.05.2022
 * Time: 11:51 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class WebhookClientException extends RuntimeException {
    private String methodName;
    private final RestClientException exception;

    public WebhookClientException(String methodName, RestClientException ex) {
        this.exception = ex;
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public void printStackTrace() {
        exception.printStackTrace();
    }



    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
