package ru.itis.stockmarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.stockmarket.dtos.InnerIdResponseDto;
import ru.itis.stockmarket.dtos.PaymentRequestDto;
import ru.itis.stockmarket.dtos.Status;
import ru.itis.stockmarket.services.PaymentService;

/**
 * Created by IntelliJ IDEA
 * Date: 17.05.2022
 * Time: 12:50 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    ResponseEntity<InnerIdResponseDto> createPayment(@RequestBody PaymentRequestDto payment) {
        InnerIdResponseDto serviceResponse = paymentService.makePaymentForContract(payment.getContractId());

        InnerIdResponseDto controllerResponse = InnerIdResponseDto
                .builder()
                .innerid(serviceResponse.getInnerid())
                .status(Status.success)
                .description("Payment successful")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(controllerResponse);
    }
}
