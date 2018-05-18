package gb.test.common;

import static lombok.AccessLevel.PRIVATE;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE, makeFinal=true)
public abstract class BeanValidationTestCase extends JUnitTestCase {
    Validator validator = Validation
            .buildDefaultValidatorFactory().getValidator();


    protected <T> Set<ConstraintViolation<T>> validate(T bean) {
        return validator.validate(bean);
    }


    protected <T> ValidationCheck<T> check(T bean) {
        return new ValidationCheck<T>(validate(bean));
    }
}
