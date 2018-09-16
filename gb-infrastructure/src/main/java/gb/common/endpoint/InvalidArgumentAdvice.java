package gb.common.endpoint;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.status;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import gb.common.dto.ValidationErrors;
import gb.common.exceptions.InvalidArgumentException;
import lombok.NonNull;


@ControllerAdvice
public class InvalidArgumentAdvice {
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ValidationErrors>
    notValid(@NonNull final InvalidArgumentException e) {
        return status(UNPROCESSABLE_ENTITY).body(new ValidationErrors(e));
    }
}
