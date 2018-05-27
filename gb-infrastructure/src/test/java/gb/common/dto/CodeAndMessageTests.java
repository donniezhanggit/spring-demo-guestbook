package gb.common.dto;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.Test;

import gb.common.JUnitTestCase;


public class CodeAndMessageTests extends JUnitTestCase {
    @Test
    public void
    When_constraint_violation_is_null_should_throw_IllegalArgumentException() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new CodeAndMessage(null));
    }
}
