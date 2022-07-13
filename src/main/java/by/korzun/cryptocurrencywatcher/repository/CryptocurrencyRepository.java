package by.korzun.cryptocurrencywatcher.repository;

import by.korzun.cryptocurrencywatcher.model.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Long> {
    Optional<Cryptocurrency> getCryptocurrencyBySymbol(String symbol);
    @Modifying
    @Query("update Cryptocurrency c set c.price = :price where c.id = :id")
    void updatePrice(@Param("id") Long id, @Param("price") Double price);
}
