package gb.dto;

import static gb.testlang.assertions.UserAssertions.assertUserEntry;
import static gb.testlang.fixtures.UsersFixtures.buildUser;
import static gb.testlang.fixtures.UsersFixtures.buildUserEntry;
import static gb.testlang.fixtures.UsersFixtures.filledUserBuilder;
import static gb.testlang.fixtures.UsersFixtures.filledUserEntryBuilder;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.model.User;


public class UserEntryTests extends JUnitTestCase {
    @Test
    public void Mapping_of_null_should_throw_NPE() {
        assertThatNullPointerException()
            .isThrownBy(() -> UserEntry.from(null));
    }


    @Test
    public void A_user_should_map_to_UserEntry() {
        // Arrange.
        final User user = buildUser();
        final UserEntry expected = buildUserEntry();

        // Act.
        final UserEntry actual = UserEntry.from(user);

        // Assert.
        assertUserEntry(expected, actual);
    }


    @Test
    public void A_userEntry_of_user_without_fullName_have_no_name() {
        // Arrange.
        final User user = filledUserBuilder()
                .fullName(null).build();
        final UserEntry expected = filledUserEntryBuilder()
                .firstName(null).lastName(null).build();

        // Act.
        final UserEntry actual = UserEntry.from(user);

        // Assert.
        assertUserEntry(expected, actual);
    }
}
