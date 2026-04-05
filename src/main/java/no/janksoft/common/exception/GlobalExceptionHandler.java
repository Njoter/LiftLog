package no.janksoft.common.exception;

import no.janksoft.common.http.ErrorResponse;
import no.janksoft.exercise.exception.DuplicateExerciseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    private ResponseEntity<ErrorResponse> errorResponse(String code, String message, int status) {
        return ResponseEntity.status(status).body(
                new ErrorResponse(code, message, status)
        );
    }
}
