package gb.common.dto;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolation;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;


@Value
@FieldDefaults(level=PRIVATE, makeFinal=true)
@AllArgsConstructor(access=PROTECTED)
public class CodeAndMessage {
    String code;
    String message;


    public CodeAndMessage(@Nonnull final ConstraintViolation<?> cv) {
        code = cv.getPropertyPath().toString();
        message = cv.getMessage();
    }
}
