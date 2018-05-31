package gb.model;

import static gb.testlang.fixtures.UsersFixtures.FIRST_NAME;
import static gb.testlang.fixtures.UsersFixtures.LAST_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import gb.common.JUnitTestCase;


public class FullNameTests extends JUnitTestCase {
    @Test
    public void A_copy_should_be_equal_source() {
        // Arrange.
        final FullName source = new FullName.FullNameBuilder()
                .firstName(FIRST_NAME).lastName(LAST_NAME).build();

        // Act.
        final FullName copy = new FullName(source);

        // Assert.
        assertThat(copy).isEqualTo(source);
    }


    @Test
    public void A_FullName_should_map_proper() {
        // Arrange.
        final FullName.FullNameBuilder builder = new FullName.FullNameBuilder()
                .firstName(FIRST_NAME).lastName(LAST_NAME);

        // Act.
        final FullName actual = builder.build();

        // Assert.
        assertThat(actual.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(actual.getLastName()).isEqualTo(LAST_NAME);
    }
}
