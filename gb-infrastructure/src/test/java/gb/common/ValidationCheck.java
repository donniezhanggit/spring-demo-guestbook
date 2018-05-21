package gb.common;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolation;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class ValidationCheck<T> {
    public static final String LENGTH_MUST_BE_BETWEEN =
            "length must be between";
    public static final String MUST_NOT_BE_NULL =
            "must not be null";

    @NonNull Set<ConstraintViolation<T>> violations;


    public ValidationError<T> hasOnlyOneError() {
        assertThat(violations.size()).isEqualTo(1);

        return new ValidationError<T>(violations.iterator().next());
    }


    public void hasNoErrors() {
        assertThat(violations.size()).isZero();
    }


    @AllArgsConstructor
    @FieldDefaults(level=PRIVATE, makeFinal=true)
    public static final class ValidationError<T> {
        @NonNull ConstraintViolation<T> violation;


        public ValidationError<T>
        forProperty(@Nonnull final String propertyName) {
            assertThat(violation.getPropertyPath().toString())
                .isEqualTo(propertyName);

            return this;
        }


        public ValidationError<T>
        withMessageContaining(@Nonnull final String substr) {
            assertThat(violation.getMessage()).contains(substr);

            return this;
        }
    }
}
