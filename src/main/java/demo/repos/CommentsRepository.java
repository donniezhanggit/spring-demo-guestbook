package demo.repos;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Repository;

import demo.model.Comment;


@Repository
public interface CommentRepository extends DataRepository<Comment, Long> {
    List<Comment> findByName(@Nonnull String name);
    List<Comment> findAllByOrderByCreatedAsc();
}
