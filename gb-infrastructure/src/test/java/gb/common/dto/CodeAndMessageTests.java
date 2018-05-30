package gb.common.dto;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import javax.validation.ConstraintViolation;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.common.guard.InvalidArgumentException;
import lombok.val;


public class CodeAndMessageTests extends JUnitTestCase {
    @Test
    public void When_ConstraintViolation_is_null_should_throw_NPE() {
        ConstraintViolation<?> _null = (ConstraintViolation<?>) null;

        assertThatNullPointerException()
            .isThrownBy(() -> new CodeAndMessage(_null));
    }


    @Test
    public void When_IllegalArgumentException_is_null_should_throw_NPE() {
        val _null = (IllegalArgumentException) null;

        assertThatNullPointerException()
            .isThrownBy(() -> new CodeAndMessage(_null));
    }


    @Test
    public void When_InvalidArgumentException_is_null_should_throw_NPE() {
        val _null = (InvalidArgumentException) null;

        assertThatNullPointerException()
            .isThrownBy(() -> new CodeAndMessage(_null));
    }
}
