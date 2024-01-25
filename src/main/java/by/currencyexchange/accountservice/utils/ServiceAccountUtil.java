package by.currencyexchange.accountservice.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;


@UtilityClass
public class ServiceAccountUtil {

    public BigDecimal round(BigDecimal value) {
        return value.setScale(2, RoundingMode.DOWN);
    }

    public boolean equal(BigDecimal firstValue, BigDecimal secondValue) {
        return firstValue.compareTo(secondValue) >= 0;
    }

    public boolean isZero(BigDecimal value) {
        return equal(value, BigDecimal.valueOf(0));
    }

    public boolean isSameCurrency(String currencyAccountFrom, String currencyAccountTo) {
        return !currencyAccountFrom.equals(currencyAccountTo);
    }
}
