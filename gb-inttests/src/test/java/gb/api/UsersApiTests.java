package gb.api;

import static gb.testlang.assertions.UserAssertions.assertUserEntryIT;
import static gb.testlang.fixtures.FullNameFixtures.buildFullName;
import static gb.testlang.fixtures.FullNameFixtures.buildFullNameInput;
import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static gb.testlang.fixtures.UsersFixtures.NON_EXISTENT_USERNAME;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import gb.common.exceptions.NotFoundException;
import gb.common.it.RecreatePerClassITCase;
import gb.dto.FullNameInput;
import gb.dto.UserEntry;
import gb.model.FullName;
import gb.testlang.assertions.UserAssertions;
import gb.testlang.fixtures.UsersFixtures;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class UsersApiTests extends RecreatePerClassITCase {
    @Autowired
    UsersFixtures fixtures;

    @Autowired
    UsersApi usersApi;

    @Autowired
    UserAssertions assertions;


    @Test
    public void An_existing_user_should_be_fetched_by_userName() {
        // Arrange.
        final String existingUserName = fixtures
                .recreateExistingUser()
                .getUserName();

        // Act.
        final Optional<UserEntry> entry = usersApi.getUser(existingUserName);

        // Assert.
        assertThat(entry.isPresent()).isTrue();
        assertUserEntryIT(entry.get());
    }


    @Test
    public void Fetching_of_non_existent_user_should_return_empty_optional() {
        // Act.
        final Optional<UserEntry> entry =
                usersApi.getUser(NON_EXISTENT_USERNAME);

        // Assert.
        assertThat(entry.isPresent()).isFalse();
    }


    @Test
    public void An_active_user_should_be_deactivated() {
        // Arrange.
        final String existingUserName = fixtures
                .recreateExistingActiveUser()
                .getUserName();

        // Act.
        usersApi.deactivateUser(existingUserName);

        // Assert.
        assertions.assertUserInactive(existingUserName);
    }


    @Test
    public void Deactivating_of_non_existent_user_throws_NotFoundException() {
        assertThatExceptionOfType(NotFoundException.class)
            .isThrownBy(() -> usersApi.deactivateUser(NON_EXISTENT_USERNAME));
    }


    @Test
    public void An_inactive_user_should_be_activated() {
        // Arrange.
        final String existingUserName = fixtures
                .recreateExistingInactiveUser()
                .getUserName();

        // Act.
        usersApi.activateUser(existingUserName);

        // Assert.
        assertions.assertUserActive(existingUserName);
    }


    @Test
    public void Activating_of_non_existent_user_throws_NotFoundException() {
        assertThatExceptionOfType(NotFoundException.class)
            .isThrownBy(() -> usersApi.activateUser(NON_EXISTENT_USERNAME));
    }


    @Test
    public void Ability_to_change_fullName_of_user() {
        // Arrange.
        final FullNameInput aNewName = buildFullNameInput();
        final FullName expectedFullName = buildFullName();
        final String userWithoutName = fixtures
                .recreateUserWithoutFullName()
                .getUserName();

        // Act.
        usersApi.changeName(userWithoutName, aNewName);

        // Assert.
        assertions.assertUserHasFullName(userWithoutName, expectedFullName);
    }


    @Test
    public void
    Changing_fullName_of_non_existent_user_throws_NotFoundException() {
        // Arrange.
        final FullNameInput aNewName = buildFullNameInput();

        // Act and assert.
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() ->
            usersApi.changeName(NON_EXISTENT_USERNAME, aNewName)
        );
    }


    @Test
    public void Ability_to_unset_fullName_of_user() {
        // Arrange.
        final String userWithName = fixtures
                .recreateUserWithFullName()
                .getUserName();

        // Act.
        usersApi.deleteName(userWithName);

        // Assert.
        assertions.assertUserHasNoFullName(userWithName);
    }


    @Test
    public void
    Removing_fullName_of_non_existent_user_throws_NotFoundException() {
        assertThatExceptionOfType(NotFoundException.class)
            .isThrownBy(() -> usersApi.deleteName(NON_EXISTENT_USERNAME));
    }
}
