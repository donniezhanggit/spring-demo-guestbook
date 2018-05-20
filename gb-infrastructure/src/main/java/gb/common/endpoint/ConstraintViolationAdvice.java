package gb.common.endpoint;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.http.ResponseEntity.status;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import gb.common.dto.ValidationErrors;


@ControllerAdvice
public class ConstraintViolationAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrors> notValid(
            @Nonnull final ConstraintViolationException e) {
        return status(PRECONDITION_FAILED).body(new ValidationErrors(e));
    }
}
