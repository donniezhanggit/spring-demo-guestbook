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
import demo.repos.CommentsRepository;
import demo.test.common.FakeData;
import demo.test.dto.CommentInputBuilder;
import demo.test.it.common.BaseRecreatePerClassITCase;

import static org.assertj.core.api.Assertions.*;


public class CommentsApiTests extends BaseRecreatePerClassITCase {
    private static final String ANON_NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED = LocalDateTime.now();
    private static final Short MIN_VERSION = 0;

    @Autowired
    private CommentsApi commentsApi;

    @Autowired
    private CommentsRepository commentRepo;


    @Override
    public void predefinedData() {
        final CommentBuilder cb = new CommentBuilder()
                .created(CREATED).name(ANON_NAME).message(MESSAGE);
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
        comment.ifPresent(this::assertCommentEntry);
    }


    @Test
    public void A_new_comment_should_be_persisted() {
        // Arrange.
        final CommentInput input = this.getCommentInputBuilder().build();

        // Act.
        final CommentEntry entry = this.commentsApi.createComment(input);
        final Optional<CommentEntry> actual = this.commentsApi
                .getComment(entry.getId());

        // Assert.
        assertThat(actual.isPresent()).isTrue();
        actual.ifPresent(this::assertCommentEntry);
    }


    @Test
    public void An_entry_should_be_returned_after_creating_new_comment() {
        // Arrange.
        final CommentInput input = this.getCommentInputBuilder().build();

        // Act.
        final CommentEntry actual = this.commentsApi.createComment(input);

        // Assert.
        this.assertCommentEntry(actual);
    }


    @Test
    public void When_a_new_comment_added_rows_count_should_increase_by_1() {
        // Arrange.
        final CommentInput input = this.getCommentInputBuilder().build();
        final long expectedQty = this.commentRepo.count() + 1;

        // Act.
        this.commentsApi.createComment(input);
        final long actualQty = this.commentRepo.count();

        // Assert.
        assertThat(actualQty).isEqualTo(expectedQty);
    }


    @Test
    public void When_name_is_too_long_expect_ValidationException() {
        // Arrange.
        final String tooLongName = FakeData.stringWithLength(
                Comment.NAME_MAX_LENGTH+1);
        final CommentInput input = this.getCommentInputBuilder()
                .name(tooLongName).build();

        // Act and assert.
        thrown.expect(ValidationException.class);
        this.commentsApi.createComment(input);
    }


    @Test
    public void When_name_length_is_max_comment_should_be_saved() {
        //Arrange.
        final String longName = FakeData.stringWithLength(
                Comment.NAME_MAX_LENGTH);
        final CommentInput input = this.getCommentInputBuilder()
                .name(longName).build();

        // Act.
        final CommentEntry entry = this.commentsApi.createComment(input);

        // Assert.
        assertThat(entry.getId()).isNotNull();
    }


    @Test
    public void When_message_is_too_long_expect_ValidationException() {
        // Arrange.
        final String tooLongMessage = FakeData.stringWithLength(
                Comment.MESSAGE_MAX_LENGTH+1);
        final CommentInput input = this.getCommentInputBuilder()
                .message(tooLongMessage).build();

        // Act and assert.
        thrown.expect(ValidationException.class);
        this.commentsApi.createComment(input);
    }


    @Test
    public void When_message_length_is_max_comment_should_be_saved() {
        //Arrange.
        final String longMessage = FakeData.stringWithLength(
                Comment.MESSAGE_MAX_LENGTH);
        final CommentInput input = this.getCommentInputBuilder()
                .message(longMessage).build();

        // Act.
        final CommentEntry entry = this.commentsApi.createComment(input);

        // Assert.
        assertThat(entry.getId()).isNotNull();
    }


    private void assertCommentEntry(final CommentEntry actual) {
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getVersion()).isEqualTo(MIN_VERSION);
        assertThat(actual.getCreated()).isNotNull();
        assertThat(actual.getMessage()).isEqualTo(MESSAGE);
        assertThat(actual.getAnonName()).isEqualTo(ANON_NAME);
        assertThat(actual.getUsername()).isNull();
    }


    private CommentInputBuilder getCommentInputBuilder() {
        return new CommentInputBuilder()
                .name(ANON_NAME).message(MESSAGE);
    }
}
