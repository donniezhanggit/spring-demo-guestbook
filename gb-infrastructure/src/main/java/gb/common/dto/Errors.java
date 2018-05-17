package gb.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import lombok.AccessLevel;
import lombok.Value;
import lombok.val;
import lombok.experimental.FieldDefaults;


@Value
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal=true)
public class Errors {
    List<CodeAndMessage> errors;


    public Errors(ConstraintViolationException e) {
        val constraintViolations = e.getConstraintViolations();

        errors = constraintViolations.stream()
                        .filter(cv -> cv != null)
                .map(cv -> new CodeAndMessage(
                                cv.getPropertyPath().toString(),
                                cv.getMessage()
                )).collect(Collectors.toList());
    }
}
