package demo.test.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import demo.api.CommentsApi;
import demo.dto.CommentEntry;
import demo.dto.CommentInput;
import demo.model.Comment;
import demo.model.CommentBuilder;
import demo.repos.CommentRepository;
import demo.test.dto.CommentEntryBuilder;
import demo.test.dto.CommentInputBuilder;


public class CommentsApiTests {
    private static final Long ID = 1L;
    private static final Short VERSION = 0;
    private static final String NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED = LocalDateTime.now();

    private final CommentRepository commentRepo =
            mock(CommentRepository.class);
    private ArgumentCaptor<Comment> captor =
            ArgumentCaptor.forClass(Comment.class);
    private final CommentsApi commentsApi = new CommentsApi(commentRepo);


    @Before
    public void setup() {
        final Comment comment = new CommentBuilder()
                .id(ID).version(VERSION).created(CREATED)
                .name(NAME).message(MESSAGE).user(null).build();

        when(commentRepo.save(any(Comment.class)))
            .thenReturn(comment);
    }


    @Test
    public void An_anonymous_comment_should_be_saved_in_repository() {
        // Arrange.
        final CommentInput input = new CommentInputBuilder()
                .name(NAME).message(MESSAGE).build();

        // Act.
        this.commentsApi.createComment(input);

        // Assert.
        verify(this.commentRepo, times(1)).save(this.captor.capture());
        this.assertCapturedCommentForSaving(this.captor.getValue());
    }


    @Test
    public void An_anonymous_comment_should_be_returned_when_saved() {
        // Arrange.
        final CommentInput input = new CommentInputBuilder()
                .name(NAME).message(MESSAGE).build();
        final CommentEntry expected = new CommentEntryBuilder()
                .id(ID).version(VERSION).created(CREATED)
                .anonName(NAME).message(MESSAGE).build();

        // Act.
        final CommentEntry actual = this.commentsApi.createComment(input);

        // Assert.
        this.assertReturnedCommentEntry(actual, expected);
    }


    private void assertReturnedCommentEntry(
            final CommentEntry actual, final CommentEntry expected) {
        assertThat(actual.getCreated())
            .isEqualByComparingTo(expected.getCreated());
        assertThat(actual.getMessage()).isEqualTo(expected.getMessage());
        assertThat(actual.getAnonName()).isEqualTo(expected.getAnonName());
        assertThat(actual.getUsername()).isNull();
        assertThat(actual.getVersion()).isEqualTo(expected.getVersion());
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }


    private void assertCapturedCommentForSaving(final Comment comment) {
        assertThat(comment).isNotNull();
        assertThat(comment.getCreated()).isNotNull();
        assertThat(comment.getMessage()).isEqualTo(MESSAGE);
        assertThat(comment.getName()).isEqualTo(NAME);
        assertThat(comment.getUser()).isEqualTo(Optional.empty());
        assertThat(comment.getVersion()).isNull();
        assertThat(comment.getId()).isNull();
    }
}
