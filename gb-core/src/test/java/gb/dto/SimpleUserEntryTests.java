package gb.dto;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.Test;

import gb.common.JUnitTestCase;


public class SimpleUserEntryTests extends JUnitTestCase {
    @Test
    public void Should_throw_NPE_when_User_is_null() {
        assertThatNullPointerException()
            .isThrownBy(() -> SimpleUserEntry.from(null));
    }
}