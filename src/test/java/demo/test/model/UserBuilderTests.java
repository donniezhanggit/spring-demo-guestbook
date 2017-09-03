package demo.test.model;

import org.junit.Test;

import demo.model.User;
import demo.model.UserBuilder;

import static org.assertj.core.api.Assertions.*;


public class UserBuilderTests {
    private static final String USERNAME = "just_user";
    private static final String PASSWORD = "V3ryS3cur3P4ssw0rD";
    private static final String EMAIL = "email@mail.net.org";


    @Test
    public void A_builder_should_return_a_new_user() {
        // Arrange and act.
        final User actual = new UserBuilder()
                .username(USERNAME).password(PASSWORD).email(EMAIL)
                .active(true).build();

        // Assert.
        this.assertUser(actual);
    }


    public void assertUser(final User actual) {
        assertThat(actual.getId()).isNull();
        assertThat(actual.getVersion()).isNull();
        assertThat(actual.getCreated()).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(USERNAME);
        assertThat(actual.getPassword()).isEqualTo(PASSWORD);
        assertThat(actual.getEmail()).isEqualTo(EMAIL);
        assertThat(actual.isActive()).isTrue();
    }
}
