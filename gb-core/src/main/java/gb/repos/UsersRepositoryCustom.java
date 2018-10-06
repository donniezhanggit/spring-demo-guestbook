package gb.repos;

import java.util.Optional;

import gb.domain.User;


public interface UsersRepositoryCustom {
    Optional<User> findByUserName(String userName);
    <T> Optional<T> findByUserName(String userName, Class<T> type);
}
