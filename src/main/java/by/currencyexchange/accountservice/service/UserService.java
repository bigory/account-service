package by.currencyexchange.accountservice.service;

import by.currencyexchange.accountservice.dto.UserDto;
import by.currencyexchange.accountservice.exception.EntityNotFoundException;
import by.currencyexchange.accountservice.mapper.UserMapper;
import by.currencyexchange.accountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getUserById(Long userId) {
        log.info("User id: {}", userId);
        return userMapper
                .toDto(userRepository
                        .findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("Invalid user id:" + userId)));
    }

}
