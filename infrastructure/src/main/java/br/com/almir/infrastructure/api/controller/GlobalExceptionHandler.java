package br.com.almir.infrastructure.api.controller;


import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.exceptions.NotFoundException;
import br.com.almir.domain.validation.Error;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<?> handleNotFoundException(final NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
  }

  @ExceptionHandler(value = DomainException.class)
  public ResponseEntity<?> handleDomainException(final DomainException ex) {
    return ResponseEntity.unprocessableEntity().body(ApiErrors.from(ex));
  }

  record ApiError(String message) {

    static ApiError from(final DomainException ex) {
      return new ApiError(ex.getMessage());
    }
  }

  record ApiErrors(List<Error> errors) {

    static ApiErrors from(final DomainException ex) {
      return new ApiErrors(ex.getErrors());
    }
  }
}
