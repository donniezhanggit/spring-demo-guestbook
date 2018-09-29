package gb.repos;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import gb.common.data.DataRepository;
import gb.domain.Comment;


@Repository
public interface CommentsRepository extends DataRepository<Comment, Long> {
    @EntityGraph(attributePaths={Comment.USER_FIELD})
    <T> List<T> findAllByOrderByCreatedAtAsc(Class<T> type);
}
