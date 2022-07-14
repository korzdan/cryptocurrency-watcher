package by.korzun.cryptocurrencywatcher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(CryptocurrencyNotFound.class)
    public ResponseEntity<Object> handleCryptocurrencyNotFoundException(CryptocurrencyNotFound e) {
        return new ResponseEntity<>(new Date() + " " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyExists.class)
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExists e) {
        return new ResponseEntity<>(new Date() + " " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

}
