package demo.repos;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(@Nonnull String username);
}
