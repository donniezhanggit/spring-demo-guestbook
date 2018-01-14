package gb.repos;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Repository;

import gb.common.data.DataRepository;
import gb.model.User;


@Repository
public interface UsersRepository extends DataRepository<User> {
    Optional<User> findByUsername(@Nonnull String username);
}
