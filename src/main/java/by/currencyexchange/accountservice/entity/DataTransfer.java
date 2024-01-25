package by.currencyexchange.accountservice.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DataTransfer {

    Long accountIdFrom;
    Long accountIdTo;
    BigDecimal amount;
    CurrencyType currencyType;

}
