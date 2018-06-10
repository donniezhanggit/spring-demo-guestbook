package gb.testlang.assertions;

import static gb.common.domain.AbstractDomainEntity.ID_FIELD;
import static gb.common.domain.AbstractDomainEntity.VERSION_FIELD;
import static gb.testlang.fixtures.UsersFixtures.buildUserEntry;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gb.dto.UserEntry;
import gb.model.FullName;
import gb.model.User;
import gb.repos.UsersRepository;


@Component
public class UsersAssertions {
    @Autowired(required=false)
    UsersRepository usersRepo;


    public void assertUserInactive(final String userName) {
        final Optional<User> user = usersRepo.findByUsername(userName);

        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().isActive()).isFalse();
    }


    public void assertUserActive(final String userName) {
        final Optional<User> user = usersRepo.findByUsername(userName);

        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().isActive()).isTrue();
    }


    public void assertUserHasFullName(final String userName,
            final FullName expectedFullName) {
        final Optional<User> user = usersRepo.findByUsername(userName);

        assertThat(user.flatMap(User::getFullName).orElse(null))
            .isEqualTo(expectedFullName);
    }


    public void assertUserHasNoFullName(final String userName) {
        assertUserHasFullName(userName, null);
    }


    public static void assertUserEntry(final UserEntry expected,
            final UserEntry actual) {
        assertThat(actual).isNotNull();
        assertThat(actual)
            .isEqualToIgnoringGivenFields(expected, ID_FIELD, VERSION_FIELD);
    }


    public static void assertUserEntryIT(final UserEntry actual) {
        final UserEntry expected = buildUserEntry();

        assertThat(actual).isNotNull();
        assertThat(actual).hasNoNullFieldsOrProperties();
        assertThat(actual)
            .isEqualToIgnoringGivenFields(expected,
                    UserEntry.ID_FIELD,
                    UserEntry.VERSION_FIELD,
                    UserEntry.REGISTERED_AT_FIELD);
    }
}
