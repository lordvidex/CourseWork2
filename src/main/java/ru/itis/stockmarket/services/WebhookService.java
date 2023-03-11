package ru.itis.stockmarket.services;

import ru.itis.stockmarket.dtos.ContractResponseDto;
import ru.itis.stockmarket.dtos.GeneralMessage;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA
 * Date: 23.05.2022
 * Time: 2:12 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public interface WebhookService {
    GeneralMessage<?> onCreateContract(ContractResponseDto contractDto, String sellerCountryCode);
    GeneralMessage<?> onPaymentMadeForContract(UUID contractId, String sellerCountryCode);
}
