package gb.model;

import static gb.testlang.fixtures.FullNameFixtures.FIRST_NAME;
import static gb.testlang.fixtures.FullNameFixtures.LAST_NAME;
import static gb.testlang.fixtures.UsersFixtures.buildUser;
import static gb.testlang.fixtures.UsersFixtures.filledUserBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import java.util.Optional;

import org.junit.Test;

import gb.common.JUnitTestCase;


public class UserTests extends JUnitTestCase {
    @Test
    public void Null_builder_should_throw_NPE() {
        assertThatNullPointerException()
            .isThrownBy(() -> new User(null));
    }


    @Test
    public void Can_not_instantiate_user_without_username() {
        // Arrange.
        final UserBuilder ub = filledUserBuilder().username(null);

        // Act and assert.
        assertThatNullPointerException()
            .isThrownBy(() -> new User(ub));
    }


    @Test
    public void Can_not_instantiate_user_without_password() {
        // Arrange.
        final UserBuilder ub = filledUserBuilder().password(null);

        // Act and assert.
        assertThatNullPointerException()
            .isThrownBy(() -> new User(ub));
    }


    @Test
    public void Can_not_instantiate_user_without_email() {
        // Arrange.
        final UserBuilder ub = filledUserBuilder().email(null);

        // Act and assert.
        assertThatNullPointerException()
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
}
