package gb.repos;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Repository;

import gb.common.data.DataRepository;
import gb.model.Comment;


@Repository
public interface CommentsRepository extends DataRepository<Comment, Long> {
    List<Comment> findByName(@Nonnull String name);
    List<Comment> findAllByOrderByCreatedAsc();
}
