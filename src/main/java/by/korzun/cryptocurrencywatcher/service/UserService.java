package by.korzun.cryptocurrencywatcher.service;

import by.korzun.cryptocurrencywatcher.dto.UserDTO;
import by.korzun.cryptocurrencywatcher.exception.CryptocurrencyNotFound;
import by.korzun.cryptocurrencywatcher.exception.UsernameAlreadyExists;
import by.korzun.cryptocurrencywatcher.model.Cryptocurrency;
import by.korzun.cryptocurrencywatcher.model.User;
import by.korzun.cryptocurrencywatcher.repository.CryptocurrencyRepository;
import by.korzun.cryptocurrencywatcher.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CryptocurrencyRepository cryptocurrencyRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User notify(UserDTO userDTO) {
        return userRepository.save(createUserFromUserDTO(userDTO));
    }

    private User createUserFromUserDTO(UserDTO userDTO) {
        if (!userRepository.existsUserByUsername(userDTO.getUsername())) {
            Cryptocurrency cryptocurrencyForNotify = findCryptocurrencyForNotify(userDTO);
            return User.builder()
                    .username(userDTO.getUsername())
                    .cryptocurrency(cryptocurrencyForNotify)
                    .startingPrice(cryptocurrencyForNotify.getPrice())
                    .build();
        } else {
            throw new UsernameAlreadyExists("This username is already taken.");
        }
    }

    private Cryptocurrency findCryptocurrencyForNotify(UserDTO userDTO) {
        return cryptocurrencyRepository
                .getCryptocurrencyBySymbol(userDTO.getSymbol())
                .orElseThrow(() -> new CryptocurrencyNotFound("Cryptocurrency hasn't been found with such a symbol."));
    }

}
