package demo.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import demo.models.User;


@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
