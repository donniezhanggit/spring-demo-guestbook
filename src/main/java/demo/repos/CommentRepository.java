package demo.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import demo.models.Comment;


@Repository
@Transactional
public interface CommentRepository extends CrudRepository<Comment, Long> {
	List<Comment> findByName(String name);
        List<Comment> findAllByOrderByCreatedAsc();
}
