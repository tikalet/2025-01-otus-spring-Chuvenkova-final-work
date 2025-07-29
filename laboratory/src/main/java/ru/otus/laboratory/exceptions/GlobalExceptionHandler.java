package ru.otus.laboratory.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.otus.laboratory.dto.ErrorDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDto> handeUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error(ex.getMessage());

        ErrorDto errorDto = new ErrorDto(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handeBadCredentialsException(BadCredentialsException ex) {
        log.error(ex.getMessage());

        ErrorDto errorDto = new ErrorDto(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handeNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage());

        ErrorDto errorDto = new ErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handeMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("", ex);

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        String errorMessage = allErrors.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("." + System.lineSeparator()));

        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorDto> handeHandlerMethodValidationException(HandlerMethodValidationException ex) {
        log.error("", ex);

        List<ParameterValidationResult> parameterValidationResults = ex.getParameterValidationResults();

        String errorMessage = parameterValidationResults.stream()
                .flatMap(parameterValidationResult -> parameterValidationResult
                        .getResolvableErrors().stream()
                        .map(MessageSourceResolvable::getDefaultMessage))
                .collect(Collectors.joining("." + System.lineSeparator()));


        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadSearchParamException.class)
    public ResponseEntity<ErrorDto> handeNotFoundException(BadSearchParamException ex) {
        log.error(ex.getMessage());

        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<ErrorDto> handeInvalidStatusException(InvalidStatusException ex) {
        log.error(ex.getMessage());

        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handeInternalError(Exception ex) {
        log.error("", ex);

        ErrorDto errorDto = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal error");
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
