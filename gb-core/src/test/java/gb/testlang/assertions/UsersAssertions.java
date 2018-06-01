package gb.testlang.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
