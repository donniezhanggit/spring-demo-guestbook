package gb.model;

import static gb.testlang.fixtures.UsersFixtures.getFilledUserBuilder;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

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
        final UserBuilder ub  = getFilledUserBuilder().username(null);

        // Act and assert.
        assertThatNullPointerException()
            .isThrownBy(() -> new User(ub));
    }


    @Test
    public void Can_not_instantiate_user_without_password() {
        // Arrange.
        final UserBuilder ub  = getFilledUserBuilder().password(null);

        // Act and assert.
        assertThatNullPointerException()
            .isThrownBy(() -> new User(ub));
    }


    @Test
    public void Can_not_instantiate_user_without_email() {
        // Arrange.
        final UserBuilder ub  = getFilledUserBuilder().email(null);

        // Act and assert.
        assertThatNullPointerException()
            .isThrownBy(() -> new User(ub));
    }
}
