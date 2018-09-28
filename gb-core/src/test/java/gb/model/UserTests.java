package gb.model;

import static gb.common.DomainEventChecker.checkThat;
import static gb.testlang.fixtures.DomainClassFixtures.doIgnoringEvents;
import static gb.testlang.fixtures.FullNameFixtures.FIRST_NAME;
import static gb.testlang.fixtures.FullNameFixtures.LAST_NAME;
import static gb.testlang.fixtures.UsersFixtures.EMAIL;
import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static gb.testlang.fixtures.UsersFixtures.buildUser;
import static gb.testlang.fixtures.UsersFixtures.filledUserBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.Optional;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.testlang.fixtures.UsersFixtures;


public class UserTests extends JUnitTestCase {
    @Test
    public void Null_builder_should_throw_IAE() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new User(null));
    }


    @Test
    public void Can_not_instantiate_user_without_username() {
        // Arrange.
        final UserBuilder ub = filledUserBuilder().userName(null);

        // Act and assert.
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new User(ub));
    }


    @Test
    public void Can_not_instantiate_user_without_password() {
        // Arrange.
        final UserBuilder ub = filledUserBuilder().password(null);

        // Act and assert.
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new User(ub));
    }


    @Test
    public void Can_not_instantiate_user_without_email() {
        // Arrange.
        final UserBuilder ub = filledUserBuilder().email(null);

        // Act and assert.
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new User(ub));
    }


    @Test
    public void User_without_fullName_should_return_empty_optional() {
        // Arrange.
        final User user = filledUserBuilder().fullName(null).build();

        // Act.
        final Optional<FullName> fullName = user.getFullName();

        // Assert.
        assertThat(fullName.isPresent()).isFalse();
    }


    @Test
    public void User_with_fullName_should_return_filled_optional() {
        // Arrange.
        final User user = filledUserBuilder()
                .fullName(FIRST_NAME, LAST_NAME).build();

        // Act.
        final Optional<FullName> fullName = user.getFullName();

        // Assert.
        assertThat(fullName.isPresent()).isTrue();
        fullName.ifPresent(fn -> {
                assertThat(fn.getFirstName()).isEqualTo(FIRST_NAME);
                assertThat(fn.getLastName()).isEqualTo(LAST_NAME);
        });
    }


    @Test
    public void Deactivating_user_should_change_active_field() {
        // Arrange.
        final User user = buildUser();

        // Act.
        user.deactivate();

        // Assert.
        assertThat(user.isActive()).isFalse();
    }


    @Test
    public void Activating_user_should_change_active_field() {
        // Arrange.
        final User user = buildUser();

        // Act.
        user.activate();

        // Assert.
        assertThat(user.isActive()).isTrue();
    }


    @Test
    public void Ability_to_change_name() {
        // Arrange.
        final User user = filledUserBuilder().fullName(null).build();
        final FullName name = new FullName(FIRST_NAME, LAST_NAME);

        // Act.
        user.changeName(name);

        // Assert.
        assertThat(user.getFullName().isPresent()).isTrue();
        assertThat(user.getFullName().get()).isEqualTo(name);
    }


    @Test
    public void Ability_to_unset_fullName_of_user() {
        // Arrange.
        final User user = filledUserBuilder()
                .fullName(FIRST_NAME, LAST_NAME).build();

        // Act.
        user.deleteName();

        // Assert.
        assertThat(user.getFullName().isPresent()).isFalse();
    }


    @Test
    public void When_user_changed_name_an_event_should_be_emitted() {
        // Arrange.
        final User user = doIgnoringEvents(
                filledUserBuilder().fullName(null)::build
        );
        final FullName name = new FullName(FIRST_NAME, LAST_NAME);

        // Act.
        user.changeName(name);

        // Assert.
        checkThat(user).hasOnlyOneEvent()
            .whichIsInstanceOf(UserFullNameChanged.class)
            .hasFieldEquals(UserFullNameChanged::getNewName, name)
            .hasFieldEquals(UserFullNameChanged::getOldName, null);
    }


    @Test
    public void When_userName_deleted_an_event_should_be_emitted() {
        // Arrange.
        final FullName name = new FullName(FIRST_NAME, LAST_NAME);
        final User user = doIgnoringEvents(
                filledUserBuilder().fullName(name)::build
        );

        // Act.
        user.deleteName();

        // Assert.
        checkThat(user).hasOnlyOneEvent()
            .whichIsInstanceOf(UserFullNameDeleted.class)
            .hasFieldEquals(UserFullNameDeleted::getOldName, name);
    }


    @Test
    public void Deactivating_of_user_should_emit_event() {
        // Arrange.
        final User user = doIgnoringEvents(UsersFixtures::buildUser);

        // Act.
        user.deactivate();

        // Assert.
        checkThat(user).hasOnlyOneEvent()
            .whichIsInstanceOf(UserDeactivated.class);
    }


    @Test
    public void Activating_of_user_should_emit_event() {
        // Arrange.
        final User user = doIgnoringEvents(UsersFixtures::buildUser);

        // Act.
        user.activate();

        // Assert.
        checkThat(user).hasOnlyOneEvent()
            .whichIsInstanceOf(UserActivated.class);
    }


    @Test
    public void Instantiation_of_user_should_emit_event() {
        // Arrange.
        final UserBuilder builder = filledUserBuilder()
                .userName(EXISTING_USERNAME).email(EMAIL);

        // Act.
        final User newUser = new User(builder);

        // Assert.
        checkThat(newUser).hasOnlyOneEvent()
            .whichIsInstanceOf(NewUserRegistered.class)
            .hasFieldEquals(NewUserRegistered::getUserName, EXISTING_USERNAME)
            .hasFieldEquals(NewUserRegistered::getEmail, EMAIL);
    }
}
