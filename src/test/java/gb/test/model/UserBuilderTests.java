package gb.test.model;

import org.junit.Test;

import gb.model.User;
import gb.model.UserBuilder;

import static org.assertj.core.api.Assertions.*;


public class UserBuilderTests {
    private static final String USERNAME = "just_user";
    private static final String PASSWORD = "V3ryS3cur3P4ssw0rD";
    private static final String EMAIL = "email@mail.net.org";


    @Test
    public void Check_building_a_new_active_user() {
        // Arrange and act.
        final User actual = new UserBuilder()
                .username(USERNAME).password(PASSWORD).email(EMAIL)
                .active(true).build();

        // Assert.
        this.assertActiveUser(actual);
    }


    @Test
    public void Check_building_a_new_inactive_user() {
        // Arrange and act.
        final User actual = new UserBuilder()
                .username(USERNAME).password(PASSWORD).email(EMAIL)
                .active(false).build();

        // Assert.
        this.assertInactiveUser(actual);
    }


    private void assertActiveUser(final User actual) {
        this.assertUser(actual);

        assertThat(actual.isActive()).isTrue();
    }


    private void assertInactiveUser(final User actual) {
        this.assertUser(actual);

        assertThat(actual.isActive()).isFalse();
    }


    private void assertUser(final User actual) {
        assertThat(actual.getId()).isNull();
        assertThat(actual.getVersion()).isNull();
        assertThat(actual.getCreated()).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(USERNAME);
        assertThat(actual.getPassword()).isEqualTo(PASSWORD);
        assertThat(actual.getEmail()).isEqualTo(EMAIL);
    }
}
