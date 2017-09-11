package gb.test.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import gb.api.CommentsApi;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.model.Comment;
import gb.model.CommentBuilder;
import gb.repos.CommentsRepository;
import gb.test.common.JUnitTestCase;
import gb.test.dto.CommentEntryBuilder;
import gb.test.dto.CommentInputBuilder;


public class CommentsApiTests extends JUnitTestCase {
    private static final Long EXISTING_ID = 1L;
    private static final Long NON_EXISTENT_ID = -1L;
    private static final Short VERSION = 0;
    private static final String NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED = LocalDateTime.now();

    private final CommentsRepository commentRepo =
            mock(CommentsRepository.class);
    private final ArgumentCaptor<Comment> commentCaptor =
            ArgumentCaptor.forClass(Comment.class);
    private final CommentsApi commentsApi = new CommentsApi(commentRepo);


    @Before
    public void setup() {
        final Comment comment = this.buildAnonComment();

        when(commentRepo.save(any(Comment.class)))
            .thenReturn(comment);
        when(commentRepo.findOne(EXISTING_ID))
            .thenReturn(Optional.of(comment));
        when(commentRepo.findOne(NON_EXISTENT_ID))
            .thenReturn(Optional.empty());
        when(commentRepo.findAllByOrderByCreatedAsc())
            .thenReturn(Arrays.asList(comment));
    }


    @Test
    public void An_anonymous_comment_should_be_fetched() {
        // Arrange.
        final CommentEntry expected = this.buildAnonCommentEntry();

        // Act.
        final Optional<CommentEntry> actual = this.commentsApi
                .getComment(EXISTING_ID);

        // Assert.
        verify(this.commentRepo, times(1)).findOne(EXISTING_ID);
        assertThat(actual.isPresent()).isTrue();
        this.assertReturnedCommentEntry(actual.get(), expected);
    }


    @Test
    public void When_comment_isnt_exist_an_empty_optional_should_returned() {
        // Arrange and act.
        final Optional<CommentEntry> actual = this.commentsApi
                .getComment(NON_EXISTENT_ID);

        // Assert.
        verify(this.commentRepo, times(1)).findOne(NON_EXISTENT_ID);
        assertThat(actual.isPresent()).isFalse();
    }


    @Test
    public void A_list_of_comments_should_be_fetched() {
        // Arrange.
        final CommentEntry expected = this.buildAnonCommentEntry();

        // Act.
        final List<CommentEntry> comments = this.commentsApi
                .getComments();

        // Assert.
        verify(this.commentRepo, times(1)).findAllByOrderByCreatedAsc();
        assertThat(comments.size()).isGreaterThan(0);
        this.assertReturnedCommentEntry(comments.get(0), expected);
    }


    @Test
    public void An_anonymous_comment_should_be_saved_in_repository() {
        // Arrange.
        final CommentInput input = this.buildAnonCommentInput();

        // Act.
        this.commentsApi.createComment(input);

        // Assert.
        verify(this.commentRepo, times(1)).save(this.commentCaptor.capture());
        this.assertCapturedCommentForSaving(this.commentCaptor.getValue());
    }


    @Test
    public void An_anonymous_comment_should_be_returned_when_saved() {
        // Arrange.
        final CommentInput input = this.buildAnonCommentInput();
        final CommentEntry expected = this.buildAnonCommentEntry();

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


    private CommentInput buildAnonCommentInput() {
        return new CommentInputBuilder()
                .name(NAME).message(MESSAGE).build();
    }


    private CommentEntry buildAnonCommentEntry() {
        return new CommentEntryBuilder()
                .id(EXISTING_ID).version(VERSION).created(CREATED)
                .anonName(NAME).message(MESSAGE).build();
    }


    private Comment buildAnonComment() {
        return new CommentBuilder()
                .id(EXISTING_ID).version(VERSION).created(CREATED)
                .name(NAME).message(MESSAGE).user(null).build();
    }
}
