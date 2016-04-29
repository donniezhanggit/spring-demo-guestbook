package demo.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import demo.models.Comment;


public interface CommentReposotory extends CrudRepository<Comment, Long> {
	List<Comment> findByName(String name);
}
