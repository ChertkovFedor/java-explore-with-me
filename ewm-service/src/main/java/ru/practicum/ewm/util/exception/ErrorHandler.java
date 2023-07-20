package ru.practicum.ewm.util.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorApi handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        Optional<FieldError> optionalFieldError = e.getBindingResult().getFieldErrors().stream().findFirst();
        String message = "";
        if (optionalFieldError.isPresent()) {
            FieldError fieldError = optionalFieldError.get();
            String fieldName = fieldError.getField();
            String value = Objects.requireNonNull(fieldError.getRejectedValue()).toString();
            String error = fieldError.getDefaultMessage();
            message = String.format("Field: %s, error: %s, value: %s", fieldName, error, value);
        }
        return errorApiBuilder(HttpStatus.BAD_REQUEST.name(), "Invalid request", message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorApi handleConstraintViolationException(final DataIntegrityViolationException e) {
        return errorApiBuilder(HttpStatus.CONFLICT.name(), "Integrity constraint violated", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorApi handleEmptyResultDataAccessException(final NotFoundException e) {
        String message = String.format("%s with id=%s not found", e.getClassName(), e.getId());
        return errorApiBuilder(HttpStatus.NOT_FOUND.name(), "Required object not found", message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorApi handleEventNotFoundException(final EventNotFoundException e) {
        return errorApiBuilder(HttpStatus.BAD_REQUEST.name(), "Required object not found", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorApi handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        return errorApiBuilder(HttpStatus.BAD_REQUEST.name(), "Invalid request", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorApi handleInvalidOperationException(final InvalidOperationException e) {
        return errorApiBuilder(HttpStatus.CONFLICT.name(), "Invalid operation", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorApi handleInvalidEventDateException(final InvalidEventDateException e) {
        return errorApiBuilder(HttpStatus.BAD_REQUEST.name(), "Invalid operation", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorApi handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        return errorApiBuilder(HttpStatus.BAD_REQUEST.name(), "Invalid request", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorApi handleThrowable(final Throwable e) {
        log.warn(e.getMessage());
        return null;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorApi handleAccessException(final AccessException e) {
        return errorApiBuilder(HttpStatus.CONFLICT.name(), "Access error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorApi handleIllegalArgumentException(final IllegalArgumentException e) {
        return errorApiBuilder(HttpStatus.BAD_REQUEST.name(), "Argument error", e.getMessage());
    }

    private ErrorApi errorApiBuilder(String status, String reason, String message) {
        log.warn(message);
        return ErrorApi.builder()
                .status(status)
                .reason(reason)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
