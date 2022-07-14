package by.korzun.cryptocurrencywatcher.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Double.compare(user.startingPrice, startingPrice) == 0
                && Objects.equals(id, user.id) && Objects.equals(username, user.username)
                && Objects.equals(cryptocurrency, user.cryptocurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, startingPrice, cryptocurrency);
    }
}
