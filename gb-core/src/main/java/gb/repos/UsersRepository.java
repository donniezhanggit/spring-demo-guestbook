package gb.repos;

import static gb.common.exceptions.Exceptions.notFound;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Repository;

import gb.common.data.DataRepository;
import gb.model.User;


@Repository
public interface UsersRepository extends DataRepository<User, Long> {
    Optional<User> findByUserName(@Nonnull String userName);
    <T> Optional<T> findByUserName(@Nonnull String userName, Class<T> type);


    default User findByUserNameOrThrow(@Nonnull String userName) {
        return findByUserName(userName)
                .orElseThrow(() -> notFound(userName));
    }


    default <T> T findByUserNameOrThrow(String userName, Class<T> type) {
        return findByUserName(userName, type)
                .orElseThrow(() -> notFound(userName));
    }
}
