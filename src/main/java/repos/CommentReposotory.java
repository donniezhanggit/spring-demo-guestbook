package repos;

import java.util.List;

import models.Comment;

import org.springframework.data.repository.CrudRepository;


public interface CommentReposotory extends CrudRepository<Comment, Long>{
	List<Comment> findByName(String name);
}
