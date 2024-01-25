package by.currencyexchange.accountservice.controller;

import by.currencyexchange.accountservice.entity.DataTransfer;
import by.currencyexchange.accountservice.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/service")
public class AccountController {

    private final TransferService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody DataTransfer dataTransfer) {
        transferService.transfer(dataTransfer);
        return ResponseEntity.ok("Transfer was completed");
    }

}
