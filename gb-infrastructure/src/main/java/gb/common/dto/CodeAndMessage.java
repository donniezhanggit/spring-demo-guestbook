package gb.common.dto;

import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PRIVATE;

import javax.validation.ConstraintViolation;

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
}
