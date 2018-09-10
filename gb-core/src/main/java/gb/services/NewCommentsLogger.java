package gb.services;

import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import org.springframework.stereotype.Service;

import gb.common.events.PersistentEventHandler;
import gb.model.Comment;
import gb.model.NewCommentAdded;
import gb.repos.CommentsRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class NewCommentsLogger implements PersistentEventHandler<NewCommentAdded> {
    @NonNull CommentsRepository commentsRepo;


    @Override
    public void handleEvent(final NewCommentAdded event) {
        log.info("Thread name: {}", Thread.currentThread().getName());
        log.info("All events after commit: {}", event);

        final Optional<Comment> comment = commentsRepo
                .findOneById(event.getCommentId());

        comment.ifPresent(c -> log.info("New Comment entity: {}", c));
    }
}
