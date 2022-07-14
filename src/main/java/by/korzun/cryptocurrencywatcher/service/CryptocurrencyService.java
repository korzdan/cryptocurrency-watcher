package by.korzun.cryptocurrencywatcher.service;

import by.korzun.cryptocurrencywatcher.dto.CryptocurrencyDTO;
import by.korzun.cryptocurrencywatcher.exception.CryptocurrencyNotFound;
import by.korzun.cryptocurrencywatcher.model.Cryptocurrency;
import by.korzun.cryptocurrencywatcher.model.User;
import by.korzun.cryptocurrencywatcher.repository.CryptocurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptocurrencyService {

    public static final String COIN_LORE_URL = "https://api.coinlore.net/api/ticker/?id=";
    public static final int MINUTE_IN_MILLISECONDS = 60000;
    public static final long[] COIN_IDS = {80, 90, 48543};
    public static final int NUMBER_OF_COINS = 3;

    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final UserService userService;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Cryptocurrency> getAvailableCryptocurrencies() {
        return cryptocurrencyRepository.findAll();
    }

    public Double getCryptocurrencyPriceBySymbol(String symbol) {
        Cryptocurrency cryptocurrency = cryptocurrencyRepository
                .getCryptocurrencyBySymbol(symbol)
                .orElseThrow(() -> new CryptocurrencyNotFound("Cryptocurrency" +
                        " with such a symbol hasn't been found"));
        return cryptocurrency.getPrice();
    }

    @Transactional
    @Scheduled(fixedRate = MINUTE_IN_MILLISECONDS)
    public void updateActualPricesOfCryptocurrencies() {
        ResponseEntity<CryptocurrencyDTO[]> response;
        for (int i = 0; i < NUMBER_OF_COINS; i++) {
            response = restTemplate.getForEntity(COIN_LORE_URL + COIN_IDS[i], CryptocurrencyDTO[].class);
            cryptocurrencyRepository.updatePrice(COIN_IDS[i],
                    Objects.requireNonNull(response.getBody())[0].getPrice_usd());
        }
        checkPriceChangingOfNotifiedCoins();
    }

    private void checkPriceChangingOfNotifiedCoins() {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            double changePercent = getCryptocurrencyPriceChangeInPercent(
                    user.getCryptocurrency().getPrice(),
                    user.getStartingPrice());
            if (Math.abs(changePercent) > 1.0) {
                log.warn("Username: " + user.getUsername() +
                        ". Symbol: " + user.getCryptocurrency().getSymbol() +
                        ". Change percent: " + changePercent);
            }
        }
    }

    private double getCryptocurrencyPriceChangeInPercent(double actualPrice, double oldPrice) {
        return (actualPrice - oldPrice) / oldPrice * 100;
    }

}
