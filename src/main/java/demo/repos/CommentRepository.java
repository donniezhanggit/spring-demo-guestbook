package demo.repos;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.Comment;


@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
	List<Comment> findByName(@Nonnull String name);
    List<Comment> findAllByOrderByCreatedAsc();
    Optional<Comment> findById(long id);
}
