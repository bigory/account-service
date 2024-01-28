package by.currencyexchange.accountservice.controller.integration;

import by.currencyexchange.accountservice.config.TestContainerConfig;
import by.currencyexchange.accountservice.dto.AccountDto;
import by.currencyexchange.accountservice.dto.UserDto;
import by.currencyexchange.accountservice.entity.CurrencyType;
import by.currencyexchange.accountservice.entity.DocumentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = TestContainerConfig.class)
@SpringBootTest
class AccountControllerIntegrTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAccountTest() throws Exception {
        Long id = 1L;
        AccountDto accountDto = AccountDto.builder()
                .id(id)
                .balance(BigDecimal.valueOf(10000.00))
                .currencyType(CurrencyType.USD.name())
                .user(UserDto.builder()
                        .id(1L)
                        .username("Ivan")
                        .documentNumber("MC0000000")
                        .documentType(DocumentType.PASSPORT.name())
                        .build())
                .build();

        mockMvc.perform(get("/api/v1/account/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountDto.getId()))
                .andExpect(jsonPath("$.balance").value(accountDto.getBalance()))
                .andExpect(jsonPath("$.currencyType").value(accountDto.getCurrencyType()))
                .andExpect(jsonPath("$.user.id").value(accountDto.getUser().getId()))
                .andExpect(jsonPath("$.user.username").value(accountDto.getUser().getUsername()))
                .andExpect(jsonPath("$.user.documentNumber").value(accountDto.getUser().getDocumentNumber()))
                .andExpect(jsonPath("$.user.documentType").value(accountDto.getUser().getDocumentType()))
                .andExpect(content().json(objectMapper.writeValueAsString(accountDto)));
    }

}
