package gb.common;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;


// TODO: Add detailed fail messages.
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class ValidationCheck<T> {
    Set<ConstraintViolation<T>> violations;


    public ValidationCheck<T> hasOnlyOneError() {
        assertThat(violations.size()).isEqualTo(1);

        return this;
    }


    public ValidationCheck<T> hasNoErrors() {
        assertThat(violations.size()).isZero();

        return this;
    }


    public ValidationCheck<T> hasErrorWithText(final String errorText) {
        // TODO: add assert and return value.
        throw new UnsupportedOperationException();
    }


    public ValidationCheck<T> hasOnlyOneErrorWithText(final String errorText) {
        return hasOnlyOneError().hasErrorWithText(errorText);
    }


    public ValidationCheck<T> hasErrorWithTextContaining(
            final String errorText) {
        // TODO: add assert and return value.
        throw new UnsupportedOperationException();
    }


    public ValidationCheck<T> hasOnlyOneErrorWithTextContaining(
            final String errorText) {
        return hasOnlyOneError().hasErrorWithTextContaining(errorText);
    }
}
