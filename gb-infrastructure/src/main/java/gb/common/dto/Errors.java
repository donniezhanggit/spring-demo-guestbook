package gb.common.dto;

import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolationException;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;


@Value
@FieldDefaults(level=PRIVATE, makeFinal=true)
@AllArgsConstructor(access=NONE)
public class Errors {
    List<CodeAndMessage> errors;


    public Errors(@Nonnull final ConstraintViolationException e) {
        errors = e.getConstraintViolations().stream()
            .filter(Objects::nonNull)
            .map(CodeAndMessage::new)
            .collect(Collectors.toList());
    }
}
