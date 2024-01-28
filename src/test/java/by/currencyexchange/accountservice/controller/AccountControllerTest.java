package by.currencyexchange.accountservice.controller;

import by.currencyexchange.accountservice.dto.AccountDto;
import by.currencyexchange.accountservice.entity.CurrencyType;
import by.currencyexchange.accountservice.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @MockBean
    private AccountService accountService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAccountTest() throws Exception {
        Long id = 1L;
        AccountDto accountDto =  AccountDto.builder()
                .id(id)
                .balance(BigDecimal.valueOf(10000.00))
                .currencyType(CurrencyType.USD.name())
                .build();

        when(accountService.getAccount(id)).thenReturn(accountDto);

        mockMvc.perform(get("/api/v1/account/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.balance").value(BigDecimal.valueOf(10000.00)))
                .andExpect(jsonPath("$.currencyType").value(CurrencyType.USD.name()))
                .andExpect(content().json(objectMapper.writeValueAsString(accountDto)));
    }

}