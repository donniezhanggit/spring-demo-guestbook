package gb.common.dto;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import javax.validation.ConstraintViolationException;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.common.validation.InvalidArgumentException;


public class ValidationErrorsTests extends JUnitTestCase {
    @Test
    public void
    When_ConstraintViolationException_is_null_should_throw_IAE() {
        final ConstraintViolationException _null = null;

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new ValidationErrors(_null));
    }


    @Test
    public void
    When_InvalidArgumentException_is_null_should_throw_IAE() {
        final InvalidArgumentException _null = null;

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new ValidationErrors(_null));
    }
}
