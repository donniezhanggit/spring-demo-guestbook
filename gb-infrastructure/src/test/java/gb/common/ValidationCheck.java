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
    public static final String LENGTH_MUST_BE_BETWEEN =
            "length must be between";
    public static final String MUST_NOT_BE_NULL =
            "must not be null";

    Set<ConstraintViolation<T>> violations;


    public ValidationError<T> hasOnlyOneError() {
        assertThat(violations.size()).isEqualTo(1);

        return new ValidationError<T>(violations.iterator().next());
    }


    public void hasNoErrors() {
        assertThat(violations.size()).isZero();
    }


    @FieldDefaults(level=PRIVATE, makeFinal=true)
    public static final class ValidationError<T> {
        ConstraintViolation<T> violation;


        public ValidationError(ConstraintViolation<T> violation) {
            this.violation = violation;
        }


        public ValidationError<T> forProperty(final String propertyName) {
            assertThat(violation.getPropertyPath().toString())
                .isEqualTo(propertyName);

            return this;
        }


        public ValidationError<T> withMessageContaining(final String substr) {
            assertThat(violation.getMessage()).contains(substr);

            return this;
        }
    }
}
