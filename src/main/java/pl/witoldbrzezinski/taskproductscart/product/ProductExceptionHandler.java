package pl.witoldbrzezinski.taskproductscart.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.witoldbrzezinski.taskproductscart.utils.HandledApiError;

import java.time.LocalDateTime;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<HandledApiError> handleProductNotFoundException(
            ProductNotFoundException exception) {
        return new ResponseEntity<>(
                new HandledApiError(LocalDateTime.now(), exception.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<HandledApiError> handleProductAlreadyExistsException(
            ProductAlreadyExistsException exception) {
        return new ResponseEntity<>(
                new HandledApiError(LocalDateTime.now(), exception.getMessage()),
                HttpStatus.CONFLICT);
    }
}
