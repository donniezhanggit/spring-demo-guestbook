package gb.common.dto;

import static lombok.AccessLevel.NONE;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;


@Value
@AllArgsConstructor(access=NONE)
public class ValidationErrors {
    List<CodeAndMessage> errors;


    public ValidationErrors(@NonNull final ConstraintViolationException e) {
        errors = e.getConstraintViolations().stream()
            .filter(Objects::nonNull)
            .map(CodeAndMessage::new)
            .collect(Collectors.toList());
    }
}
