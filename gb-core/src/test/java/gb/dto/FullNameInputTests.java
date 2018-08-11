package gb.dto;

import static gb.testlang.fixtures.FullNameFixtures.buildFullName;
import static gb.testlang.fixtures.FullNameFixtures.buildFullNameInput;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.model.FullName;

public class FullNameInputTests extends JUnitTestCase {
    @Test
    public void A_FullNameInput_to_FullName() {
        // Arrange.
        final FullNameInput input = buildFullNameInput();
        final FullName expected = buildFullName();

        // Act.
        final FullName actual = input.toFullName();

        // Assert.
        assertThat(actual).isEqualTo(expected);
    }
}
