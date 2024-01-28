package by.currencyexchange.accountservice.controller.integration;

import by.currencyexchange.accountservice.config.TestContainerConfig;
import by.currencyexchange.accountservice.dto.AccountDto;
import by.currencyexchange.accountservice.entity.CurrencyType;
import by.currencyexchange.accountservice.entity.DataTransfer;
import by.currencyexchange.accountservice.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = TestContainerConfig.class)
@SpringBootTest
class TransferControllerIntegrTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountService accountService;

    @Transactional
    @Test
    void transfer_thenStatus200() throws Exception {
        DataTransfer dataTransfer = DataTransfer
                .builder()
                .accountIdFrom(1L)
                .accountIdTo(5L)
                .currencyType(CurrencyType.USD)
                .amount(BigDecimal.valueOf(1))
                .build();

        AccountDto accountDtoFromCurrent = accountService.getAccount(1L);
        AccountDto accountDtoToCurrent = accountService.getAccount(5L);

        mockMvc.perform(post("/api/v1/service/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dataTransfer)))
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer was completed"));

        AccountDto accountDtoFromNew = accountService.getAccount(1L);

        BigDecimal debitAccountFrom = accountDtoFromCurrent.getBalance().subtract(dataTransfer.getAmount());
        assertEquals(debitAccountFrom, accountDtoFromNew.getBalance());

        AccountDto accountDtoToNew = accountService.getAccount(5L);

        BigDecimal refillAccountTo = accountDtoToCurrent.getBalance().add(dataTransfer.getAmount());
        assertEquals(refillAccountTo, accountDtoToNew.getBalance());
    }

    @Test
    void transfer_thenStatus400_NotAccountInCurrencyException() throws Exception {
        AccountDto accountDto = accountService.getAccount(1L);
        String currencyType = accountDto.getCurrencyType();
        CurrencyType nonAccountCurrencyType = Arrays.stream(CurrencyType.values())
                .filter(valueType -> !valueType.name().equals(currencyType)).findFirst().get();
        DataTransfer dataTransfer = DataTransfer
                .builder()
                .accountIdFrom(1L)
                .accountIdTo(5L)
                .currencyType(nonAccountCurrencyType)
                .amount(BigDecimal.valueOf(500.00))
                .build();

        String messageException = "Message exception: No account in this currency " + dataTransfer.getCurrencyType() + " for userId: " + dataTransfer.getAccountIdFrom();

        mockMvc.perform(post("/api/v1/service/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dataTransfer)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(messageException));
    }

    @Test
    void transfer_thenStatus400_InsufficientFundsException() throws Exception {
        AccountDto accountDto = accountService.getAccount(1L);
        BigDecimal amount = accountDto.getBalance().add(BigDecimal.valueOf(100));
        DataTransfer dataTransfer = DataTransfer
                .builder()
                .accountIdFrom(1L)
                .accountIdTo(5L)
                .currencyType(CurrencyType.USD)
                .amount(amount)
                .build();
        String messageException = "Message exception: Insufficient funds in the account " + 1;

        mockMvc.perform(post("/api/v1/service/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dataTransfer)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(messageException));
    }

}
