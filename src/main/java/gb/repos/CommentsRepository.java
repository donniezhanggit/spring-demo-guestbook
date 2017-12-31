package gb.repos;

import java.util.List;
import org.springframework.stereotype.Repository;

import gb.common.data.DataRepository;
import gb.model.Comment;


@Repository
public interface CommentsRepository extends DataRepository<Comment> {
    <T> List<T> findAllByOrderByCreatedAsc(Class<T> type);
}
