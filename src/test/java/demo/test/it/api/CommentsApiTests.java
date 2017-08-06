package demo.test.it.api;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import demo.api.CommentsApi;
import demo.dto.CommentEntry;
import demo.dto.CommentInput;
import demo.model.Comment;
import demo.model.CommentBuilder;
import demo.repos.CommentRepository;
import demo.test.dto.CommentInputBuilder;
import demo.test.it.common.BaseRecreatePerClassITCase;

import static org.assertj.core.api.Assertions.*;


public class CommentsApiTests extends BaseRecreatePerClassITCase {
    private static final String NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED = LocalDateTime.now();
    private static final Short MIN_VERSION = 0;

    @Autowired
    private CommentsApi commentsApi;

    @Autowired
    private CommentRepository commentRepo;


    @Override
    public void predefinedData() {
        final CommentBuilder cb = new CommentBuilder()
                .created(CREATED).name(NAME).message(MESSAGE);
        final Comment comment1 = cb.build();
        final Comment comment2 = cb.build();

        commentRepo.save(Arrays.asList(comment1, comment2));
    }


    @Test
    public void Comments_should_be_fetched() {
        // Arrange and act.
        final List<CommentEntry> comments = this.commentsApi.getComments();

        // Assert.
        assertThat(comments.size()).isGreaterThan(0);
    }


    @Test
    public void A_comment_by_id_should_be_fetched() {
        // Arrange.
        final long commentId = this.commentsApi.getComments().get(0).getId();

        // Act.
        final Optional<CommentEntry> comment = this.commentsApi
                .getComment(commentId);

        // Assert.
        assertThat(comment.isPresent()).isTrue();
        comment.ifPresent(c -> this.assertCommentEntry(c, commentId));
    }


    @Test
    public void A_comment_should_be_created() {
        // Arrange.
        final CommentInput input = new CommentInputBuilder()
                .name(NAME).message(MESSAGE).build();

        // Act.
        final CommentEntry entry = this.commentsApi.createComment(input);
        final Optional<CommentEntry> actual = this.commentsApi
                .getComment(entry.getId());

        // Assert.
        assertThat(actual.isPresent()).isTrue();
        actual.ifPresent(a -> this.assertCommentEntry(a, entry.getId()));
    }


    @Test
    public void When_name_is_longer_than_max_expect_ValidationException() {
        // Arrange.
        final CommentInput input = new CommentInputBuilder()
                .name("123456789012345678901").message(MESSAGE).build();

        // Act and assert.
        thrown.expect(ValidationException.class);
        this.commentsApi.createComment(input);
    }


    @Test
    public void When_name_length_is_max_comment_should_be_saved() {
        //Arrange.
        final CommentInput input = new CommentInputBuilder()
                .name("12345678901234567890").message(MESSAGE).build();

        // Act.
        final CommentEntry entry = this.commentsApi.createComment(input);

        // Assert.
        assertThat(entry.getId()).isNotNull();
    }
    
    
    private void assertCommentEntry(
            final CommentEntry actual, final long expectedId) {
        assertThat(actual.getCreated()).isNotNull();
        assertThat(actual.getMessage()).isEqualTo(MESSAGE);
        assertThat(actual.getAnonName()).isEqualTo(NAME);
        assertThat(actual.getUsername()).isNull();
        assertThat(actual.getVersion()).isEqualTo(MIN_VERSION);
        assertThat(actual.getId()).isEqualTo(expectedId);
    }
}
