package ru.practicum.ewm.util.exception;

public class InvalidEventDateException extends RuntimeException {
    public InvalidEventDateException(String message) {
        super(message);
    }
}