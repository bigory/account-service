package by.currencyexchange.accountservice.controller;

import by.currencyexchange.accountservice.dto.TransactionDto;
import by.currencyexchange.accountservice.entity.TransactionType;
import by.currencyexchange.accountservice.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @MockBean
    private TransactionService transactionService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTransactionTest() throws Exception {
        String id = "36b79703-403c-4ec2-8187-6b2c50ab8ff5";
        LocalDateTime localDateTime = LocalDateTime.now();

        TransactionDto transactionDto = TransactionDto.builder()
                .id(id)
                .transactionType(TransactionType.DEBIT.name())
                .transactionTime(localDateTime)
                .amount(BigDecimal.valueOf(500.00))
                .build();

        when(transactionService.getTransactionById(id)).thenReturn(transactionDto);

        mockMvc.perform(get("/api/v1/transactions/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("36b79703-403c-4ec2-8187-6b2c50ab8ff5"))
                .andExpect(jsonPath("$.transactionType").value(TransactionType.DEBIT.name()))
                .andExpect(jsonPath("$.transactionTime").value(localDateTime.toString()))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(500.00)))
                .andExpect(content().json(objectMapper.writeValueAsString(transactionDto)));
    }

}