package gb.common.guard;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.common.exceptions.InvalidArgumentException;
import lombok.val;


public class GuardTests extends JUnitTestCase {
    private static final String MESSAGE = "message";
    private static final String CODE = "code";


    @Test
    public void Null_code_should_throw_IAE() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Guard.that(true, null, MESSAGE));
    }


    @Test
    public void Null_message_should_throw_IAE() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Guard.that(true, CODE, null));
    }


    @Test
    public void Falsy_condition_should_throw_InvalidArgumentException() {
        // Arrange.
        val expected = new InvalidArgumentException(CODE, MESSAGE);

        // Act and assert.
        assertThatThrownBy(() -> Guard.that(false, CODE, MESSAGE))
            .isEqualToComparingFieldByField(expected);
    }


    @Test
    public void Truthy_condition_should_not_throw() {
        Guard.that(true, CODE, MESSAGE);
    }
}
