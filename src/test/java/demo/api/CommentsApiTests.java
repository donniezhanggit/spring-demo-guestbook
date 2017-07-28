package demo.api;

import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.BeforeTransaction;

import demo.common.BaseRecreatePerClassITCase;
import demo.dto.CommentEntry;
import demo.dto.CommentInput;
import demo.dto.CommentInputBuilder;
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


    @Override
    public void predefinedDataTx() {
        commentRepo.save(new CommentBuilder()
                .created(CREATED).name(NAME).message(MESSAGE).build()
        );

        commentRepo.save(new CommentBuilder()
                .created(CREATED).name(NAME).message(MESSAGE).build()
        );
    }


    @Test
    public void CommentsShouldBeFetched() {
        final List<CommentEntry> comments = this.commentsApi.getComments();

        assertTrue(comments.size() > 0);
    }


    @Test
    public void CommentByIdShouldBeFetched() {
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


    @Test
    public void CommentShouldBeCreated() {
        // Arrange.
        final CommentInput input = new CommentInputBuilder()
                .name(NAME).message(MESSAGE).build();

        // Act.
        final CommentEntry entry = this.commentsApi.createComment(input);
        final Optional<CommentEntry> actual = this.commentsApi
                .getComment(entry.getId());

        // Assert.
        assertTrue(actual.isPresent());

        actual.ifPresent(a -> {
            assertTrue("created",  a.getCreated() != null);
            assertTrue("message",  a.getMessage().equals(MESSAGE));
            assertTrue("anonname", a.getAnonName().equals(NAME));
            assertTrue("username", a.getUsername() == null);
            assertTrue("version",  a.getVersion() == 0);
            assertTrue("id",       a.getId() == entry.getId());
        });
    }
}
