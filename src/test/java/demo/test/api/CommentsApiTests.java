package demo.test.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import demo.api.CommentsApi;
import demo.dto.CommentEntry;
import demo.dto.CommentInput;
import demo.model.CommentBuilder;
import demo.repos.CommentRepository;
import demo.test.common.BaseRecreatePerClassITCase;
import demo.test.dto.CommentInputBuilder;

import static org.assertj.core.api.Assertions.*;


public class CommentsApiTests extends BaseRecreatePerClassITCase {
    private static final String NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED = LocalDateTime.now();

    @Autowired
    private CommentsApi commentsApi;

    @Autowired
    private CommentRepository commentRepo;


    @Override
    public void predefinedData() {
        commentRepo.save(new CommentBuilder()
                .created(CREATED).name(NAME).message(MESSAGE).build()
        );

        commentRepo.save(new CommentBuilder()
                .created(CREATED).name(NAME).message(MESSAGE).build()
        );
    }


    @Test
    public void Comments_should_be_fetched() {
        final List<CommentEntry> comments = this.commentsApi.getComments();

        assertThat(comments.size()).isGreaterThan(0);
    }


    @Test
    public void A_comment_by_id_should_be_fetched() {
        final Optional<CommentEntry> comment = this.commentsApi
                .getComment(10000);

        assertThat(comment.isPresent()).isTrue();

        comment.ifPresent(c -> {
            assertThat(c.getCreated()).isNotNull();
            assertThat(c.getMessage()).isEqualTo(MESSAGE);
            assertThat(c.getAnonName()).isEqualTo(NAME);
            assertThat(c.getUsername()).isNull();
            assertThat(c.getVersion()).isEqualTo((short) 0);
            assertThat(c.getId()).isEqualTo(10000);
        });
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

        actual.ifPresent(a -> {
            assertThat(a.getCreated()).isNotNull();
            assertThat(a.getMessage()).isEqualTo(MESSAGE);
            assertThat(a.getAnonName()).isEqualTo(NAME);
            assertThat(a.getUsername()).isNull();
            assertThat(a.getVersion()).isEqualTo((short) 0);
            assertThat(a.getId()).isEqualTo(entry.getId());
        });
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
    public void When_name_is_max_comment_should_be_saved() {
        //Arrange.
        final CommentInput input = new CommentInputBuilder()
                .name("12345678901234567890").message(MESSAGE).build();

        // Act.
        final CommentEntry entry = this.commentsApi.createComment(input);

        // Assert.
        assertThat(entry.getId()).isNotNull();
    }
}