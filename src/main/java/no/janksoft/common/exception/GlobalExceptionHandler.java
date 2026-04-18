package no.janksoft.common.exception;

import lombok.extern.slf4j.Slf4j;
import no.janksoft.common.http.ErrorResponse;
import no.janksoft.exercise.exception.DuplicateExerciseException;
import no.janksoft.exercise.exception.ExerciseNotFoundException;
import no.janksoft.user.exception.DuplicateUserException;
import no.janksoft.user.exception.UserNotFoundException;
import no.janksoft.workout.exception.WorkoutSetNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
@Slf4j
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

    @ExceptionHandler(WorkoutSetNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWorkoutSetNotFoundException(
            WorkoutSetNotFoundException e
    ) {
        return errorResponse(
                "WORKOUT_SET_NOT_FOUND",
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

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataAccessApiUsageException(
            InvalidDataAccessApiUsageException e
    ) {
        return errorResponse(
                "INVALID_DATA_ACCESS_USAGE",
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException e
    ) {
        String code = "DATABASE_CONSTRAINT_VIOLATION";
        String message = "Database constraint violation";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Throwable rootCause = e.getMostSpecificCause();

        if (rootCause instanceof SQLException) {
            SQLException sqlException = (SQLException) rootCause;
            String sqlState = sqlException.getSQLState();

            switch (sqlState) {
                case "23502" -> {
                    code = "NOT_NULL_VIOLATION";
                    String key = extractColumnNameFromNotNullViolation(e);
                    message = String.format("Field '%s' cannot be null", key);
                }
            }
        }

        return errorResponse(
                code,
                message,
                status.value()
        );
    }

    private String extractColumnNameFromNotNullViolation(DataIntegrityViolationException e) {
        String message = e.getMostSpecificCause().getMessage();

        Pattern pattern = Pattern.compile("column \"([^\"]+)\"");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "unknown";
    }

    private ResponseEntity<ErrorResponse> errorResponse(String code, String message, int status) {
        return ResponseEntity.status(status).body(
                new ErrorResponse(code, message, status)
        );
    }
}
