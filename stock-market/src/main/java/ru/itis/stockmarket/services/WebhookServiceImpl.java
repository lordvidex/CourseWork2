package ru.itis.stockmarket.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.itis.stockmarket.dtos.ContractResponseDto;
import ru.itis.stockmarket.dtos.GeneralMessage;
import ru.itis.stockmarket.dtos.PaymentRequestDto;
import ru.itis.stockmarket.dtos.Status;
import ru.itis.stockmarket.exceptions.WebhookClientException;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA
 * Date: 23.05.2022
 * Time: 2:15 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@Service
@Slf4j
public class WebhookServiceImpl implements WebhookService {

    private final RestTemplate restTemplate;

    private final String BASE_URL = "http://188.93.211.195";

    WebhookServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    @Retryable(
            value = WebhookClientException.class,
            maxAttempts = 10,
            backoff = @Backoff(multiplier = 2, delay = 10000))
    public GeneralMessage<?> onCreateContract(ContractResponseDto contractDto, String sellerCountryCode) {
        String url = BASE_URL + "/" + sellerCountryCode + "/contract";
        try {
            log.info(String.format("onCreateContract for contract %s", contractDto.getContractId()));
            HttpEntity<ContractResponseDto> request = new HttpEntity<>(contractDto);
            GeneralMessage<?> response = restTemplate.postForObject(url, request, GeneralMessage.class);
            logResponse("onCreateContract to " + url, response);
            return response;
        } catch (RestClientException ex) {
            log.warn("Retrying request " + ex.getLocalizedMessage());
            throw new WebhookClientException("onCreateContract", ex);
        }
    }

    @Override
    @Retryable(
            value = WebhookClientException.class,
            maxAttempts = 10,
            backoff = @Backoff(multiplier = 2, delay = 10000))
    public GeneralMessage<?> onPaymentMadeForContract(UUID contractId, String sellerCountryCode) {
        try {
            log.info(String.format("onPaymentMadeForContract for contract %s", contractId));
            HttpEntity<PaymentRequestDto> request = new HttpEntity<>(
                    PaymentRequestDto.builder()
                            .contractId(contractId)
                            .build());
            String url = BASE_URL + "/" + sellerCountryCode + "/payment";
            GeneralMessage<?> response = restTemplate.postForObject(url, request, GeneralMessage.class);
            logResponse("onPaymentMadeForContract", response);
            return response;
        } catch (RestClientException ex) {
            log.warn("Retrying request " + ex.getLocalizedMessage());
            throw new WebhookClientException("onPaymentMadeForContract", ex);
        }
    }

    @Recover
    public GeneralMessage<?> recover(WebhookClientException ex, ContractResponseDto contractDto, String sellerCountryCode) {
        ex.printStackTrace();
        log.error(String.format("Error thrown while sending new contract %s from country %s, method '%s' with description %s",
                contractDto.getContractId(),
                sellerCountryCode,
                ex.getMethodName(),
                ex.getLocalizedMessage()));
        return null;
    }

    @Recover
    public GeneralMessage<?> recover(WebhookClientException ex, UUID contractId, String sellerCountryCode) {
        ex.printStackTrace();
        log.error(String.format("Error thrown while paying for %s from Country %s, from method '%s' with description %s",
                contractId,
                sellerCountryCode,
                ex.getMethodName(),
                ex.getLocalizedMessage()));
        return null;
    }

    private void logResponse(String methodName, GeneralMessage<?> response) {
        if (response == null) {
            log.error(methodName + ": Null Response from request");
        } else if (response.getStatus() == Status.failure) {
            log.error(methodName + ": Returned with status 'failure' and description " + response.getDescription());
        } else {
            log.info(methodName + ": Returned with status 'success' and description " + response.getDescription());
        }
    }
}
