package by.currencyexchange.accountservice.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceAccountUtilTest {

    @Test
    void roundTest() {
        assertEquals(BigDecimal.valueOf(1.23), ServiceAccountUtil.round(BigDecimal.valueOf(1.2345)));
    }

    @Test
    void equalTest_shouldReturnTrue() {
        assertTrue(ServiceAccountUtil.equal(BigDecimal.valueOf(10), BigDecimal.valueOf(10)));
    }

    @Test
    void equalTest_shouldReturnFalse() {
        assertFalse(ServiceAccountUtil.equal(BigDecimal.valueOf(9), BigDecimal.valueOf(10)));
    }

    @Test
    void isZeroTest() {
        assertTrue(ServiceAccountUtil.isZero(BigDecimal.valueOf(0)));
    }

    @Test
    void isSameCurrencyTest_shouldReturnFalse() {
        assertFalse(ServiceAccountUtil.isSameCurrency("USD", "USD"));
    }

    @Test
    void isSameCurrencyTest_shouldReturnTrue() {
        assertTrue(ServiceAccountUtil.isSameCurrency("USD", "EUR"));
    }

}