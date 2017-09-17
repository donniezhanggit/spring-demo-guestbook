package gb.test.it.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import gb.model.User;
import gb.model.UserBuilder;
import gb.repos.UsersRepository;
import gb.test.common.FakeData;
import gb.test.it.common.RecreatePerClassITCase;


public class UserTests extends RecreatePerClassITCase {
    private static final Long MINIMAL_ID = 10000L;
    private static final Short MINIMAL_VERSION = 0;
    private static final String USERNAME1 = "A_just_new_user";
    private static final String PASSWORD = "V3ryS3cur3P4ssw0rD";
    private static final String EMAIL = "admin@guestbook.gb";

    @Autowired
    UsersRepository usersRepo;


    @Override
    protected void predefinedData() {
        final String username = USERNAME1;
        final User user = this.getUserBuilder().username(username).build();

        this.usersRepo.save(user);
    }


    @Test
    public void A_user_should_be_fetched() {
        // Arrange.
        final long existingUserId = this.usersRepo.findAll().get(0).getId();
        final User expected = this.getUserBuilder()
                .username(USERNAME1).build();

        // Act.
        final Optional<User> actual = this.usersRepo.findOne(existingUserId);

        // Assert.
        assertThat(actual.isPresent()).isTrue();
        this.assertUser(actual.get(), expected);
    }


    @Test
    public void A_new_user_should_be_saved() {
        // Arrange.
        final String newUsername = "new_user1";
        final User expected = this.getUserBuilder()
                .username(newUsername).build();

        // Act.
        final User actual = this.usersRepo.save(expected);

        // Assert.
        this.assertUser(actual, expected);
    }


    @Test
    public void An_user_with_long_username_should_be_saved() {
        // Arrange.
        final String longUsername = FakeData.stringWithLength(
                User.USERNAME_MAX_LENGTH);
        final User expected = this.getUserBuilder()
                .username(longUsername).build();

        // Act.
        final User actual = this.usersRepo.save(expected);

        // Assert.
        this.assertUser(actual, expected);
    }


    @Test
    public void An_user_with_too_long_username_should_throw_exception() {
        // Arrange.
        final String tooLongUsername = FakeData.stringWithLength(
                User.USERNAME_MAX_LENGTH+1);
        final User expected = this.getUserBuilder()
                .username(tooLongUsername).build();

        // Act and assert.
        thrown.expect(TransactionSystemException.class);
        this.usersRepo.save(expected);
    }


    @Test
    public void An_user_with_long_password_should_be_saved() {
        // Arrange.
        final String uniqueUsername = "Another_user1";
        final String longPassword = FakeData.stringWithLength(
                User.PASSWORD_MAX_LENGTH);
        final User expected = this.getUserBuilder()
                .username(uniqueUsername)
                .password(longPassword).build();

        // Act.
        final User actual = this.usersRepo.save(expected);

        // Assert.
        this.assertUser(actual, expected);
    }


    @Test
    public void An_user_with_too_long_password_should_throw_exception() {
        // Arrange.
        final String uniqueUsername = "Another_user2";
        final String tooLongPassword = FakeData.stringWithLength(
                User.PASSWORD_MAX_LENGTH+1);
        final User expected = this.getUserBuilder()
                .username(uniqueUsername)
                .password(tooLongPassword).build();

        // Act and assert.
        thrown.expect(TransactionSystemException.class);
        this.usersRepo.save(expected);
    }


    @Test
    public void An_user_with_long_email_should_be_saved() {
        // Arrange.
        final String uniqueUsername = "Another_user3";
        final String tooLongEmail = FakeData.emailWithLength(
                User.EMAIL_MAX_LENGTH);
        final User expected = this.getUserBuilder()
                .username(uniqueUsername)
                .email(tooLongEmail).build();

        // Act.
        final User actual = this.usersRepo.save(expected);

        // Assert.
        this.assertUser(actual, expected);
    }


    @Test
    public void An_user_with_too_long_email_should_throw_exception() {
        // Arrange.
        final String uniqueUsername = "Another_user4";
        final String tooLongEmail = FakeData.emailWithLength(
                User.EMAIL_MAX_LENGTH+1);
        final User expected = this.getUserBuilder()
                .username(uniqueUsername)
                .email(tooLongEmail).build();

        // Act and assert.
        thrown.expect(TransactionSystemException.class);
        this.usersRepo.save(expected);
    }


    @Test
    public void An_user_with_wrong_email_should_throw_exception() {
        // Arrange.
        final String uniqueUsername = "Another_user5";
        final String badEmail = "Not_a_valid_email";
        final User expected = this.getUserBuilder()
                .username(uniqueUsername)
                .email(badEmail).build();

        // Act and assert.
        thrown.expect(TransactionSystemException.class);
        this.usersRepo.save(expected);
    }


    @Test
    public void Setters_and_getters_should_work_properly() {
        // Arrange.
        final String anotherUsername = "another name";
        final String anotherPassword = "another password";
        final String anotherEmail = "another@email.org";
        final boolean active = false;
        final User input = this.getUserBuilder().build();

        final User expected = this.getUserBuilder()
                .username(anotherUsername)
                .password(anotherPassword)
                .email(anotherEmail)
                .active(active).build();

        input.setUsername(anotherUsername);
        input.setPassword(anotherPassword);
        input.setEmail(anotherEmail);
        input.setActive(active);

        // Act.
        final User actual = this.usersRepo.save(input);

        // Assert.
        this.assertUser(actual, expected);
    }


    private void assertUser(final User actual, final User expected) {
        assertThat(actual.getId()).isGreaterThanOrEqualTo(MINIMAL_ID);
        assertThat(actual.getVersion())
            .isGreaterThanOrEqualTo(MINIMAL_VERSION);
        assertThat(actual.getUsername()).isEqualTo(expected.getUsername());
        assertThat(actual.getCreated()).isNotNull();
        assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getComments()).isNotNull();
    }


    private UserBuilder getUserBuilder() {
        return new UserBuilder()
                .password(PASSWORD)
                .email(EMAIL).active(true);
    }
}
