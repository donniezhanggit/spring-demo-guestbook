package gb.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.Test;

import gb.common.JUnitTestCase;


public class UserDeactivatedTests extends JUnitTestCase {
    @Test
    public void
    An_instantiation_of_event_without_aggregate_should_throw_IAE() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> UserDeactivated.of(null));
    }
}
