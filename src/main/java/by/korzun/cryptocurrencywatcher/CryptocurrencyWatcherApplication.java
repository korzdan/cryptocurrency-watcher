package by.korzun.cryptocurrencywatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptocurrencyWatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptocurrencyWatcherApplication.class, args);
    }

}
