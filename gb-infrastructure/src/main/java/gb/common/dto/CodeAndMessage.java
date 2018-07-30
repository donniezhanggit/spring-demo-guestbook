package gb.common.dto;

import static lombok.AccessLevel.NONE;

import javax.validation.ConstraintViolation;

import gb.common.exceptions.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;


@Value
@AllArgsConstructor(access=NONE)
public class CodeAndMessage {
    String code;
    String message;


    public CodeAndMessage(@NonNull final ConstraintViolation<?> cv) {
        code = cv.getPropertyPath().toString();
        message = cv.getMessage();
    }


    public CodeAndMessage(@NonNull final InvalidArgumentException e) {
        code = e.getCode();
        message = e.getMessage();
    }
}
