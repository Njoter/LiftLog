package no.janksoft.user.service;

import lombok.RequiredArgsConstructor;
import no.janksoft.user.dto.CreateUserRequest;
import no.janksoft.user.dto.UserLoginRequest;
import no.janksoft.user.dto.UserResponse;
import no.janksoft.user.exception.DuplicateUserException;
import no.janksoft.user.exception.UserNotFoundException;
import no.janksoft.user.model.User;
import no.janksoft.user.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(CreateUserRequest request) {
        try {
            User user = new User(request.name());

            User saved = userRepository.save(user);
            return toResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateUserException(request.name());
        }
    }

    public UserResponse login(UserLoginRequest request) {
        User user = userRepository.findByName(request.name())
                .orElseThrow(UserNotFoundException::new);

        return toResponse(user);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName()
        );
    }
}
