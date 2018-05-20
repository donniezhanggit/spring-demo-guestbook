package gb.common.endpoint;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import gb.common.dto.ValidationErrors;


@ControllerAdvice
public class ConstraintViolationAdvice {
    @ResponseStatus(PRECONDITION_FAILED)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationErrors
    notValid(@Nonnull final ConstraintViolationException e) {
        return new ValidationErrors(e);
    }
}
