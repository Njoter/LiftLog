package no.janksoft.common.exception;

import no.janksoft.common.http.ErrorResponse;
import no.janksoft.exercise.exception.DuplicateExerciseException;
import no.janksoft.exercise.exception.ExerciseNotFoundException;
import no.janksoft.user.exception.DuplicateUserException;
import no.janksoft.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateExerciseException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateExerciseException(
            DuplicateExerciseException e
    ) {
        return errorResponse(
                "DUPLICATE_EXERCISE",
                e.getMessage(),
                HttpStatus.CONFLICT.value()
        );
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserException(
            DuplicateUserException e
    ) {
        return errorResponse(
                "DUPLICATE_USER",
                e.getMessage(),
                HttpStatus.CONFLICT.value()
        );
    }

    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExerciseNotFoundException(
            ExerciseNotFoundException e
    ) {
        return errorResponse(
                "EXERCISE_NOT_FOUND",
                e.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException e
    ) {
        return errorResponse(
                "USER_NOT_FOUND",
                e.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        Map<String, String> errors = new HashMap<>();

        String message = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return errorResponse(
                "INVALID_REQUEST_FIELDS",
                message,
                HttpStatus.BAD_REQUEST.value()
        );
    }

    private ResponseEntity<ErrorResponse> errorResponse(String code, String message, int status) {
        return ResponseEntity.status(status).body(
                new ErrorResponse(code, message, status)
        );
    }
}
