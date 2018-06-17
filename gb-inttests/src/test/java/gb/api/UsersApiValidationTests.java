package gb.api;

import static gb.common.FakeData.stringWithLength;
import static gb.model.FullName.FIRST_NAME_MAX_LENGTH;
import static gb.model.FullName.LAST_NAME_MAX_LENGTH;
import static gb.testlang.fixtures.FullNameFixtures.filledFullNameInputBuilder;
import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static lombok.AccessLevel.PRIVATE;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import gb.common.it.RecreatePerClassITCase;
import gb.dto.FullNameInput;
import gb.testlang.fixtures.UsersFixtures;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class UsersApiValidationTests extends RecreatePerClassITCase {
    @Autowired
    UsersFixtures fixtures;

    @Autowired
    UsersApi usersApi;


    @Test
    public void When_firstName_is_too_long_expect_ValidationException() {
        // Arrange.
        final String userName = fixtures.recreateExistingUser().getUserName();
        final String tooLongFirstName =
                stringWithLength(FIRST_NAME_MAX_LENGTH+1);
        final FullNameInput input = filledFullNameInputBuilder()
                .firstName(tooLongFirstName).build();

        // Act and assert.
        assertThatInvalid(() -> usersApi.changeName(userName, input));
    }


    @Test
    public void When_firstName_length_is_max_fullName_should_be_changed() {
        // Arrange.
        final String userName = fixtures.recreateExistingUser().getUserName();
        final String tooLongFirstName =
                stringWithLength(FIRST_NAME_MAX_LENGTH);
        final FullNameInput input = filledFullNameInputBuilder()
                .firstName(tooLongFirstName).build();

        // Act. Must not throw.
        usersApi.changeName(userName, input);
    }


    @Test
    public void When_lastName_is_too_long_expect_ValidationException() {
        // Arrange.
        final String userName = fixtures.recreateExistingUser().getUserName();
        final String tooLongLastName =
                stringWithLength(LAST_NAME_MAX_LENGTH+1);
        final FullNameInput input = filledFullNameInputBuilder()
                .lastName(tooLongLastName).build();

        // Act and assert.
        assertThatInvalid(() -> usersApi.changeName(userName, input));
    }


    @Test
    public void When_lastName_length_is_max_fullName_should_be_changed() {
        // Arrange.
        final String userName = fixtures.recreateExistingUser().getUserName();
        final String tooLongLastName =
                stringWithLength(LAST_NAME_MAX_LENGTH);
        final FullNameInput input = filledFullNameInputBuilder()
                .firstName(tooLongLastName).build();

        // Act. Must not throw.
        usersApi.changeName(userName, input);
    }


    @Test
    public void When_firstName_is_null_expect_ValidationException() {
        // Arrange.
        final String userName = fixtures.recreateExistingUser().getUserName();
        final FullNameInput input = filledFullNameInputBuilder()
                .firstName(null).build();

        // Act and assert.
        assertThatInvalid(() -> usersApi.changeName(userName, input));
    }


    @Test
    public void When_lastName_is_null_expect_ValidationException() {
        // Arrange.
        final String userName = fixtures.recreateExistingUser().getUserName();
        final FullNameInput input = filledFullNameInputBuilder()
                .lastName(null).build();

        // Act and assert.
        assertThatInvalid(() -> usersApi.changeName(userName, input));
    }
}
