package gb.dto;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.Test;

import gb.common.JUnitTestCase;


public class SimpleUserEntryTests extends JUnitTestCase {
    @Test
    public void Should_throw_IAE_when_User_is_null() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> SimpleUserEntry.from(null));
    }
}
