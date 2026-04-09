package no.janksoft.user.controller;

import lombok.RequiredArgsConstructor;
import no.janksoft.user.dto.CreateUserRequest;
import no.janksoft.user.dto.UserLoginRequest;
import no.janksoft.user.dto.UserResponse;
import no.janksoft.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base-path}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody CreateUserRequest request
    ) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("login")
    public ResponseEntity<UserResponse> login(
            @RequestBody UserLoginRequest request
    ) {
        UserResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
