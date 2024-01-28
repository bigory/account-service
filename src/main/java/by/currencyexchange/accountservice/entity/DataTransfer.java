package by.currencyexchange.accountservice.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class DataTransfer {

    Long accountIdFrom;
    Long accountIdTo;
    BigDecimal amount;
    CurrencyType currencyType;

}
