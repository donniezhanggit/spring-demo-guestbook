package gb.model;

import static gb.testlang.fixtures.FullNameFixtures.FIRST_NAME;
import static gb.testlang.fixtures.FullNameFixtures.LAST_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import gb.common.JUnitTestCase;
import lombok.val;


public class FullNameTests extends JUnitTestCase {
    @Test
    public void A_copy_should_be_equal_source() {
        // Arrange.
        val source = new FullName(FIRST_NAME, LAST_NAME);

        // Act.
        val copy = new FullName(source);

        // Assert.
        assertThat(copy).isEqualTo(source);
    }


    @Test
    public void A_FullName_should_map_proper() {
        // Arrange and act.
        val actual = new FullName(FIRST_NAME, LAST_NAME);

        // Assert.
        assertThat(actual.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(actual.getLastName()).isEqualTo(LAST_NAME);
    }
}
