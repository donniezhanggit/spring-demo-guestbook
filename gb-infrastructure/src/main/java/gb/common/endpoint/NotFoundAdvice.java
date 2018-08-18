package gb.common.endpoint;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.status;

import javax.annotation.Nonnull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import gb.common.exceptions.NotFoundException;


@ControllerAdvice
public class NotFoundAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void>
    notFound(@Nonnull final NotFoundException e) {
        return status(NOT_FOUND).build();
    }
}
