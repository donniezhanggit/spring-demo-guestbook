package gb.common.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import javax.validation.ConstraintViolation;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.common.exceptions.InvalidArgumentException;
import lombok.val;


public class CodeAndMessageTests extends JUnitTestCase {
    private static final String CODE = "code";
    private static final String MESSAGE = "message";


    @Test
    public void When_ConstraintViolation_is_null_should_throw_NPE() {
        ConstraintViolation<?> _null = (ConstraintViolation<?>) null;

        assertThatNullPointerException()
            .isThrownBy(() -> new CodeAndMessage(_null));
    }


    @Test
    public void When_InvalidArgumentException_is_null_should_throw_NPE() {
        val _null = (InvalidArgumentException) null;

        assertThatNullPointerException()
            .isThrownBy(() -> new CodeAndMessage(_null));
    }


    @Test
    public void InvalidArgumentException_shlould_map_to_CodeAndMessage() {
        // Act.
        val com = new CodeAndMessage(
                new InvalidArgumentException(CODE, MESSAGE)
        );

        // Assert.
        assertThat(com.getCode()).isEqualTo(CODE);
        assertThat(com.getMessage()).isEqualTo(MESSAGE);
    }
}
