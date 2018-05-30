package gb.common.dto;

import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PRIVATE;

import javax.validation.ConstraintViolation;

import gb.common.guard.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;


@Value
@FieldDefaults(level=PRIVATE, makeFinal=true)
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


    public CodeAndMessage(@NonNull final IllegalArgumentException e) {
        code = "BAD_REQUEST";
        message = e.getMessage();
    }
}
