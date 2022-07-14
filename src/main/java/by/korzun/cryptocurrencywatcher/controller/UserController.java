package by.korzun.cryptocurrencywatcher.controller;

import by.korzun.cryptocurrencywatcher.dto.UserDTO;
import by.korzun.cryptocurrencywatcher.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/notify")
    public ResponseEntity<Object> notify(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.notify(userDTO), HttpStatus.OK);
    }

}
