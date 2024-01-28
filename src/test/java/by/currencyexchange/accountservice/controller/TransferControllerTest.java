package by.currencyexchange.accountservice.controller;

import by.currencyexchange.accountservice.entity.CurrencyType;
import by.currencyexchange.accountservice.entity.DataTransfer;
import by.currencyexchange.accountservice.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
class TransferControllerTest {

    @MockBean
    private TransferService transferService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void transfer() throws Exception {
        DataTransfer dataTransfer = DataTransfer.builder()
                .accountIdTo(1L)
                .accountIdFrom(5L)
                .currencyType(CurrencyType.USD)
                .amount(BigDecimal.valueOf(500))
                .build();

        doNothing().when(transferService).transfer(any(DataTransfer.class));

        mockMvc.perform(post("/api/v1/service/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dataTransfer)))
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer was completed"));
    }

}
