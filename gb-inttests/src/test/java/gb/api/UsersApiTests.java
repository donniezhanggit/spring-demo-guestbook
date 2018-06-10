package gb.api;

import static gb.testlang.assertions.UsersAssertions.assertUserEntryIT;
import static gb.testlang.fixtures.FullNameFixtures.buildFullName;
import static gb.testlang.fixtures.FullNameFixtures.buildFullNameInput;
import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import gb.common.it.RecreatePerClassITCase;
import gb.dto.FullNameInput;
import gb.dto.UserEntry;
import gb.model.FullName;
import gb.testlang.assertions.UsersAssertions;
import gb.testlang.fixtures.UsersFixtures;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class UsersApiTests extends RecreatePerClassITCase {
    @Autowired
    UsersFixtures usersFixtures;

    @Autowired
    UsersApi usersApi;

    @Autowired
    UsersAssertions assertions;


    @Test
    public void A_user_should_be_fetched_by_userName() {
        // Arrange.
        final String existingUserName = usersFixtures
                .recreateExistingUser()
                .getUserName();

        // Act.
        final Optional<UserEntry> entry = usersApi.getUser(existingUserName);

        // Assert.
        assertThat(entry.isPresent()).isTrue();
        assertUserEntryIT(entry.get());
    }


    @Test
    public void An_active_user_should_be_deactivated() {
        // Arrange.
        final String existingUserName = usersFixtures
                .recreateExistingActiveUser()
                .getUserName();

        // Act.
        usersApi.deactivateUser(existingUserName);

        // Assert.
        assertions.assertUserInactive(existingUserName);
    }


    @Test
    public void An_inactive_user_should_be_activated() {
        // Arrange.
        final String existingUserName = usersFixtures
                .recreateExistingInactiveUser()
                .getUserName();

        // Act.
        usersApi.activateUser(existingUserName);

        // Assert.
        assertions.assertUserActive(existingUserName);
    }


    @Test
    public void Ability_to_change_fullName_of_user() {
        // Arrange.
        final FullNameInput aNewName = buildFullNameInput();
        final FullName expectedFullName = buildFullName();
        final String userWithoutName = usersFixtures
                .recreateUserWithoutFullName()
                .getUserName();

        // Act.
        usersApi.changeName(userWithoutName, aNewName);

        // Assert.
        assertions.assertUserHasFullName(userWithoutName, expectedFullName);
    }


    @Test
    public void Ability_to_unset_fullName_of_user() {
        // Arrange.
        final String userWithName = usersFixtures
                .recreateUserWithFullName()
                .getUserName();

        // Act.
        usersApi.deleteName(userWithName);

        // Assert.
        assertions.assertUserHasNoFullName(userWithName);
    }
}
