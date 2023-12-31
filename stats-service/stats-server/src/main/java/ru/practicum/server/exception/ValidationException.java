package ru.practicum.server.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationException extends RuntimeException {

    public ValidationException(final String m) {
        super(m);
        log.warn("Validation failed: " + m);
    }

}
