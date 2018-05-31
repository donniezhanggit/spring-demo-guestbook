package gb.model;

import static gb.testlang.fixtures.UsersFixtures.FIRST_NAME;
import static gb.testlang.fixtures.UsersFixtures.LAST_NAME;
import static gb.testlang.fixtures.UsersFixtures.getFilledUserBuilder;
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
        final UserBuilder ub = getFilledUserBuilder().username(null);

        // Act and assert.
        assertThatNullPointerException()
            .isThrownBy(() -> new User(ub));
    }


    @Test
    public void Can_not_instantiate_user_without_password() {
        // Arrange.
        final UserBuilder ub = getFilledUserBuilder().password(null);

        // Act and assert.
        assertThatNullPointerException()
            .isThrownBy(() -> new User(ub));
    }


    @Test
    public void Can_not_instantiate_user_without_email() {
        // Arrange.
        final UserBuilder ub = getFilledUserBuilder().email(null);

        // Act and assert.
        assertThatNullPointerException()
            .isThrownBy(() -> new User(ub));
    }


    @Test
    public void User_without_fullName_should_return_empty_optional() {
        // Arrange.
        final User user = getFilledUserBuilder().fullName(null).build();

        // Act.
        final Optional<FullName> fullName = user.getFullName();

        // Assert.
        assertThat(fullName.isPresent()).isFalse();
    }


    @Test
    public void User_with_fullName_should_return_filled_optional() {
        // Arrange.
        final User user = getFilledUserBuilder()
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
}
