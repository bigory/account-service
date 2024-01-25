package by.currencyexchange.accountservice.dto;


import by.currencyexchange.accountservice.entity.DocumentType;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String documentNumber;
    private DocumentType documentType;

}
