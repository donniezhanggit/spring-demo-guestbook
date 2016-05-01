package demo.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import demo.models.Comment;


@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
	List<Comment> findByName(String name);
}
