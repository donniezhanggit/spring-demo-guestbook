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
    private static final String BAD_REQUEST_CODE = "BAD_REQUEST";


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


    public CodeAndMessage(@NonNull final IllegalArgumentException e) {
        code = BAD_REQUEST_CODE;
        message = e.getMessage();
    }
}
