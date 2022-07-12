package by.korzun.cryptocurrencywatcher.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cryptocurrencies")
@Getter
@Setter
public class Cryptocurrency {
    @Id
    private Long id;
    private String symbol;
    private double price;
}
