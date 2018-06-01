package gb.api;

import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import gb.common.it.RecreatePerClassITCase;
import gb.dto.UserEntry;
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
        final String existingUserName = usersFixtures.existingUser()
                .getUsername();

        // Act.
        final Optional<UserEntry> entry = usersApi.getUser(existingUserName);

        // Assert.
        Assertions.assertThat(entry.isPresent()).isTrue();
    }


    @Test
    public void A_user_should_be_deactivated() {
        // Arrange.
        final String existingUserName = usersFixtures.existingActiveUser()
                .getUsername();

        // Act.
        usersApi.deactivateUser(existingUserName);

        // Assert.
        assertions.assertUserInactive(existingUserName);
    }
}
