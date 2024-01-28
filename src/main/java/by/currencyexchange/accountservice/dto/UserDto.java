package by.currencyexchange.accountservice.dto;


import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class UserDto {

    private Long id;
    private String username;
    private String documentNumber;
    private String documentType;

}
