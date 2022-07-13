package by.korzun.cryptocurrencywatcher.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(name = "starting_price")
    private double startingPrice;

    @ManyToOne
    @JoinColumn(name = "cryptocurrency_id")
    private Cryptocurrency cryptocurrency;
}
