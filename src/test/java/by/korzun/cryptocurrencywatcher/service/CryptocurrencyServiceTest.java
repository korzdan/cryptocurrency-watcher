package by.korzun.cryptocurrencywatcher.service;

import by.korzun.cryptocurrencywatcher.model.Cryptocurrency;
import by.korzun.cryptocurrencywatcher.repository.CryptocurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CryptocurrencyServiceTest {

    private static final Long BTC_ID = 80L;
    private static final String BTC_SYMBOL = "BTC";
    private static final double BTC_PRICE = 2000.0;

    private CryptocurrencyService cryptocurrencyService;

    @Mock
    private CryptocurrencyRepository cryptocurrencyRepository;
    @Mock
    private UserService userService;

    private Cryptocurrency cryptocurrency;

    @BeforeEach
    void setUp() {
        cryptocurrencyService = new CryptocurrencyService(cryptocurrencyRepository, userService);
        cryptocurrency = new Cryptocurrency(BTC_ID, BTC_SYMBOL, BTC_PRICE);
    }

    @Test
    @DisplayName("Invoke findAll() when calling getAvailableCryptocurrencies")
    void InvokeFindAll_WhenCallingGetAvailableCryptocurrencies() {
        cryptocurrencyService.getAvailableCryptocurrencies();
        verify(cryptocurrencyRepository).findAll();
    }

    @Test
    @DisplayName("Return price of BTC coin when call getCryptocurrencyPriceBySymbol")
    void ReturnBTCCoinPrice_WhenCallGetCryptocurrencyPriceBySymbol() {
        when(cryptocurrencyRepository.getCryptocurrencyBySymbol(BTC_SYMBOL)).thenReturn(Optional.of(cryptocurrency));
        double price = cryptocurrencyService.getCryptocurrencyPriceBySymbol(BTC_SYMBOL);
        assertEquals(BTC_PRICE, price);
    }

}