package gb.common.dto;

import static lombok.AccessLevel.NONE;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import gb.common.validation.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;


@Value
@AllArgsConstructor(access=NONE)
public class ValidationErrors {
    List<ValidationError> errors;


    public ValidationErrors(@NonNull final ConstraintViolationException e) {
        errors = e.getConstraintViolations().stream()
            .filter(Objects::nonNull)
            .map(ValidationError::new)
            .collect(Collectors.toList());
    }


    public ValidationErrors(@NonNull final InvalidArgumentException e) {
        errors = Collections.singletonList(new ValidationError(e));
    }
}
