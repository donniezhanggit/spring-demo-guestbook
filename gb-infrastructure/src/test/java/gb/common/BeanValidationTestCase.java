package gb.common;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static lombok.AccessLevel.PRIVATE;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE, makeFinal=true)
public abstract class BeanValidationTestCase extends JUnitTestCase {
    Validator validator = buildDefaultValidatorFactory().getValidator();


    protected <T> Set<ConstraintViolation<T>>
    validate(@NonNull final T bean) {
        return validator.validate(bean);
    }


    protected <T> ValidationCheck<T> check(@NonNull final T bean) {
        return new ValidationCheck<T>(validate(bean));
    }
}
