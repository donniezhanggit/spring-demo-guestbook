package demo.api;

import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import demo.common.BaseRecreatePerClassITCase;
import demo.dto.CommentEntry;
import demo.model.CommentBuilder;
import demo.repos.CommentRepository;


public class CommentsApiTests extends BaseRecreatePerClassITCase {
    private static final String NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED = LocalDateTime.now();

    @Autowired
    private CommentsApi commentsApi;

    @Autowired
    private CommentRepository commentRepo;



    @Before
    public void setup() {
        commentRepo.save(
                new CommentBuilder()
                .created(CREATED)
                .name(NAME)
                .message(MESSAGE)
                .build()
        );

        commentRepo.save(
                new CommentBuilder()
                .created(CREATED)
                .name(NAME)
                .message(MESSAGE)
                .build()
        );
    }


    @Test
    public void testGetComments() {
        final List<CommentEntry> comments = this.commentsApi.getComments();

        assertTrue(comments.size() == 2);
    }


    @Test
    public void testGetComment() {
        final Optional<CommentEntry> comment = this.commentsApi.getComment(1);

        assertTrue(comment.isPresent());

        comment.ifPresent(c -> {
            assertTrue("created",  c.getCreated().compareTo(CREATED) == 0);
            assertTrue("message",  c.getMessage().equals(MESSAGE));
            assertTrue("anonname", c.getAnonName().equals(NAME));
            assertTrue("username", c.getUsername() == null);
            assertTrue("version",  c.getVersion() == 0);
            assertTrue("id",       c.getId() == 1);
        });
    }
}
