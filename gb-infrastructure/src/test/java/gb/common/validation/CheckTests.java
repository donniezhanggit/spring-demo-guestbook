package gb.common.validation;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.common.validation.Check;
import lombok.val;


public class CheckTests extends JUnitTestCase {
    private static final String MESSAGE = "message";
    private static final String CODE = "code";


    @Test
    public void Null_code_should_throw_IAE() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Check.that(true, null, MESSAGE));
    }


    @Test
    public void Null_message_should_throw_IAE() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Check.that(true, CODE, null));
    }


    @Test
    public void Falsy_condition_should_throw_InvalidArgumentException() {
        // Arrange.
        val expected = new InvalidArgumentException(CODE, MESSAGE);

        // Act and assert.
        assertThatThrownBy(() -> Check.that(false, CODE, MESSAGE))
            .isEqualToComparingFieldByField(expected);
    }


    @Test
    public void Truthy_condition_should_not_throw() {
        Check.that(true, CODE, MESSAGE);
    }
}
