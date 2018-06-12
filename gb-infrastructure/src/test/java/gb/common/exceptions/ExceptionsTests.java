package gb.common.exceptions;

import static gb.common.exceptions.Exceptions.notFound;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import gb.common.JUnitTestCase;


public class ExceptionsTests extends JUnitTestCase {
    private static final String MESSAGE_TEMPLATE =
            "Can not identify object by id: ";


    @Test
    public void notFound_should_return_NotFoundException() {
        // Arrange.
        final String userName = "admin";
        final String expected = MESSAGE_TEMPLATE +  userName;

        // Act.
        final NotFoundException actual = notFound(userName);

        // Assert.
        assertThat(actual.getMessage()).isEqualTo(expected);
    }


    @Test
    public void notFound_should_handle_null_object_id_proper() {
        // Arrange.
        final String userName = null;
        final String expected = MESSAGE_TEMPLATE +  "null";

        // Act.
        final NotFoundException actual = notFound(userName);

        // Assert.
        assertThat(actual.getMessage()).isEqualTo(expected);
    }
}
