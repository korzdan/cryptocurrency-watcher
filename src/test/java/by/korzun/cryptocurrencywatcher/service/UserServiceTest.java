package by.korzun.cryptocurrencywatcher.service;

import by.korzun.cryptocurrencywatcher.dto.UserDTO;
import by.korzun.cryptocurrencywatcher.exception.UsernameAlreadyExists;
import by.korzun.cryptocurrencywatcher.model.Cryptocurrency;
import by.korzun.cryptocurrencywatcher.model.User;
import by.korzun.cryptocurrencywatcher.repository.CryptocurrencyRepository;
import by.korzun.cryptocurrencywatcher.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final String USERNAME = "roker3";
    public static final Long BTC_ID = 80L;
    public static final String BTC_SYMBOL = "BTC";
    public static double BTC_ACTUAL_PRICE = 4000;

    @Mock
    private UserRepository userRepository;
    @Mock
    private CryptocurrencyRepository cryptocurrencyRepository;

    private UserService userService;
    private Cryptocurrency cryptocurrency;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, cryptocurrencyRepository);
        cryptocurrency = new Cryptocurrency(BTC_ID, BTC_SYMBOL, BTC_ACTUAL_PRICE);
        userDTO = new UserDTO(USERNAME, BTC_SYMBOL);
    }

    @Test
    @DisplayName("Verify invocation of findAll() when calling getAllUsers()")
    void ReturnAllUsers_WhenInvokeMethodInService() {
        userService.getAllUsers();
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Return User from UserDTO when invoke createUserFromUserDTO()")
    void ReturnUserFromUserDTO_WhenInvokeCreateUserFromUserDTO() {
        when(userRepository.existsUserByUsername(USERNAME)).thenReturn(false);
        when(cryptocurrencyRepository.getCryptocurrencyBySymbol(BTC_SYMBOL))
                .thenReturn(Optional.of(cryptocurrency));

        User userFromDTO = ReflectionTestUtils.invokeMethod(userService, "createUserFromUserDTO", userDTO);

        userService.notify(userDTO);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(userFromDTO, capturedUser);
    }

    @Test
    @DisplayName("Throw UsernameAlreadyExists when invoke createUserFromUserDTO() with existing username")
    void ThrowUsernameAlreadyExists_WhenInvokeCreateUserFromUserDTOWithExistingUsername() {
        when(userRepository.existsUserByUsername(USERNAME)).thenReturn(true);
        assertThrows(UsernameAlreadyExists.class, () -> userService.notify(userDTO));
    }

}