package by.korzun.cryptocurrencywatcher.exception;

public class CryptocurrencyNotFound extends RuntimeException {
    public CryptocurrencyNotFound(String message) {
        super(message);
    }
}
