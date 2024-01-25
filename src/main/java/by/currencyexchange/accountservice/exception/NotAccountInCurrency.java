package by.currencyexchange.accountservice.exception;

public class NotAccountInCurrency extends RuntimeException {
    public NotAccountInCurrency(String message) {
        super(message);
    }
}
