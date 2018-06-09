package gb.api;

import static gb.testlang.fixtures.FullNameFixtures.buildFullName;
import static gb.testlang.fixtures.FullNameFixtures.buildFullNameInput;
import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import org.assertj.core.api.Assertions;
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
                .getUsername();

        // Act.
        final Optional<UserEntry> entry = usersApi.getUser(existingUserName);

        // Assert.
        Assertions.assertThat(entry.isPresent()).isTrue();
    }


    @Test
    public void An_active_user_should_be_deactivated() {
        // Arrange.
        final String existingUserName = usersFixtures
                .recreateExistingActiveUser()
                .getUsername();

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
                .getUsername();

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
                .getUsername();

        // Act.
        usersApi.changeName(userWithoutName, aNewName);

        // Assert.
        assertions.assertUsersFullName(userWithoutName, expectedFullName);
    }


    @Test
    public void Ability_to_unset_fullName_of_user() {
        // Arrange.
    }
}
