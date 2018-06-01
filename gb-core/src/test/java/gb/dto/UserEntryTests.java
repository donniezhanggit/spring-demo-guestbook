package gb.dto;

import static gb.testlang.fixtures.UsersFixtures.buildUser;
import static gb.testlang.fixtures.UsersFixtures.buildUserEntry;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.model.User;
import gb.testlang.assertions.UsersAssertions;


public class UserEntryTests extends JUnitTestCase {
    @Test
    public void Mapping_of_null_should_throw_NPE() {
        assertThatNullPointerException()
            .isThrownBy(() -> UserEntry.from(null));
    }


    @Test
    public void A_user_shouldMap_to_UserEntry() {
        // Arrange.
        final User user = buildUser();
        final UserEntry expected = buildUserEntry();

        // Act.
        final UserEntry actual = UserEntry.from(user);

        // Assert.
        UsersAssertions.assertUserEntry(expected, actual);
    }
}
