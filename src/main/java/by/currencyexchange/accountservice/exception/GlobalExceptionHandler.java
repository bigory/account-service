package by.currencyexchange.accountservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, SQLException.class})
    public ResponseEntity<?> handleAllExceptions(Exception exception) {
        log.error("{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Message exception: " + exception.getMessage());
    }

    @ExceptionHandler({EntityNotFoundException.class, InsufficientFundsException.class, NotAccountInCurrencyException.class})
    public ResponseEntity<?> handleException(RuntimeException exception) {
        log.error("{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message exception: " + exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleHttpException(HttpClientErrorException exception) {
        log.error("{} {}", exception.getStatusCode(), exception.getStatusText());
        return ResponseEntity.status(exception.getStatusCode()).body("Message exception:  " + exception.getStatusCode() + " " + exception.getMessage());
    }

}
