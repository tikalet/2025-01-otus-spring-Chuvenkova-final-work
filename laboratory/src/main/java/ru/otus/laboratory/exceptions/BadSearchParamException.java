package ru.otus.laboratory.exceptions;

public class BadSearchParamException extends RuntimeException {
    public BadSearchParamException(String message) {
        super(message);
    }
}
