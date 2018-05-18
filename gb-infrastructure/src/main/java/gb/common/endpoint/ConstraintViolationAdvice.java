package gb.common.endpoint;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import gb.common.dto.Errors;


@ControllerAdvice
public class ConstraintViolationAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Errors> notValid(ConstraintViolationException e) {
        return ResponseEntity.status(PRECONDITION_FAILED)
                        .body(new Errors(e));
    }
}
