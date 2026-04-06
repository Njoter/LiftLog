package no.janksoft.common.exception;

import no.janksoft.common.http.ErrorResponse;
import no.janksoft.exercise.exception.DuplicateExerciseException;
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
