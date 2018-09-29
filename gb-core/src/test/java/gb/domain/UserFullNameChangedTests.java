package gb.domain;

import static gb.testlang.fixtures.FullNameFixtures.FIRST_NAME;
import static gb.testlang.fixtures.FullNameFixtures.LAST_NAME;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.Test;

import gb.common.JUnitTestCase;


public class UserFullNameChangedTests extends JUnitTestCase {
    @Test
    public void
    An_instantiation_of_event_without_aggregate_should_throw_IAE() {
        // Arrange.
        final FullName newName = new FullName(FIRST_NAME, LAST_NAME);

        // Act and assert.
        assertThatIllegalArgumentException()
            .isThrownBy(() -> UserFullNameChanged.of(null, newName));
    }
}
