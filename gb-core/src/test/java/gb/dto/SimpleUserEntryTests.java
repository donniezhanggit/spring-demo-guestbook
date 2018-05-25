package gb.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Test;

import gb.common.JUnitTestCase;
import lombok.val;


public class SimpleUserEntryTests extends JUnitTestCase {
    @Test
    public void Should_throw_NPE_when_User_is_null() {
        // Act.
        val throwable = catchThrowable(() -> SimpleUserEntry.from(null));

        // Assert.
        assertThat(throwable).isInstanceOf(NullPointerException.class);
    }
}
