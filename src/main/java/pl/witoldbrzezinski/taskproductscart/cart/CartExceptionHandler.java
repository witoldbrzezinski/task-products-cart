package pl.witoldbrzezinski.taskproductscart.cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.witoldbrzezinski.taskproductscart.product.ProductAlreadyExistsException;
import pl.witoldbrzezinski.taskproductscart.product.ProductNotFoundException;
import pl.witoldbrzezinski.taskproductscart.utils.HandledApiError;

import java.time.LocalDateTime;

@ControllerAdvice
public class CartExceptionHandler {

  @ExceptionHandler(CartNotFoundException.class)
  public ResponseEntity<HandledApiError> handleCartNotFoundException(
      CartNotFoundException exception) {
    return new ResponseEntity<>(
        new HandledApiError(LocalDateTime.now(), exception.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NoSuchItemInCartException.class)
  public ResponseEntity<HandledApiError> handleNoSuchItemInCartException(
      NoSuchItemInCartException exception) {
    return new ResponseEntity<>(
        new HandledApiError(LocalDateTime.now(), exception.getMessage()), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(CartTooLargeException.class)
  public ResponseEntity<HandledApiError> handleTooLargeCartException(
          CartTooLargeException exception) {
    return new ResponseEntity<>(
            new HandledApiError(LocalDateTime.now(), exception.getMessage()), HttpStatus.CONFLICT);
  }
}
