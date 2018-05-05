package gb.test.common;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


public abstract class BeanValidationTestCase extends JUnitTestCase {
        protected <T> Set<ConstraintViolation<T>> validate(T bean) {
                final ValidatorFactory factory =
                                Validation.buildDefaultValidatorFactory();
                final Validator validator = factory.getValidator();

                return validator.validate(bean);
        }
}
