package by.korzun.cryptocurrencywatcher.controller;

import by.korzun.cryptocurrencywatcher.model.Cryptocurrency;
import by.korzun.cryptocurrencywatcher.service.CryptocurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptocurrencies")
public class CryptocurrencyController {

    private final CryptocurrencyService cryptocurrencyService;

    @GetMapping
    public ResponseEntity<List<Cryptocurrency>> getAvailableCryptocurrencies() {
        return new ResponseEntity<>(cryptocurrencyService
                .getAvailableCryptocurrencies(), HttpStatus.OK);
    }

    @GetMapping("/{symbol}/price")
    public ResponseEntity<Double> getCryptocurrencyPriceBySymbol(@PathVariable String symbol) {
        return new ResponseEntity<>(cryptocurrencyService
                .getCryptocurrencyPriceBySymbol(symbol), HttpStatus.OK);
    }

}
