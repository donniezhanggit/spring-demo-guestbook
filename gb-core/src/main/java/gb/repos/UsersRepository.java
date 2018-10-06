package gb.repos;

import static gb.common.exceptions.Exceptions.notFound;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import gb.common.data.DataRepository;
import gb.domain.User;


@Repository
public interface UsersRepository
extends DataRepository<User, Long>, UsersRepositoryCustom {
    <T> List<T> findAllByOrderByCreatedAtAsc(Class<T> type);
    <T> Optional<T> findByUserName(String userName, Class<T> type);


    default User findByUserNameOrThrow(String userName) {
        return findByUserName(userName)
                .orElseThrow(() -> notFound(userName));
    }


    default <T> T findByUserNameOrThrow(String userName, Class<T> type) {
        return findByUserName(userName, type)
                .orElseThrow(() -> notFound(userName));
    }
}
