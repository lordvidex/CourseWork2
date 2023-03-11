package ru.itis.stockmarket.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.stockmarket.dtos.BankResponseDto;
import ru.itis.stockmarket.dtos.Status;
import ru.itis.stockmarket.services.BankService;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by IntelliJ IDEA
 * Date: 06.06.2022
 * Time: 12:28 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@AutoConfigureMockMvc
@WebMvcTest(BankController.class)
public class BankControllerTests {
    /* unused beans */
    @MockBean
    private UserDetailsService _userDetailsService;
    @MockBean
    private PasswordEncoder _passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankService bankService;
    private ObjectMapper jsonMapper;

    @BeforeEach
    public void initialize() {
        this.jsonMapper = new ObjectMapper();
    }

    @Test
    void getAllBankSuccess() throws Exception {
        // Given
        BankResponseDto dto = BankResponseDto.builder()
                .name("Test bank")
                .address("Test Address")
                .countryCode("ru")
                .build();
        var jsonMap = jsonMapper.convertValue(dto, Map.class);

        // When
        when(bankService.getAllOrganizations()).thenReturn(List.of(dto));

        // Then
        mockMvc.perform(get("/bank"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status").value(Status.success.name()))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0]").value(jsonMap));

    }
}
