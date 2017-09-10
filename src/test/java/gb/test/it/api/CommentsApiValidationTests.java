package gb.test.it.api;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ValidationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gb.api.CommentsApi;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.model.Comment;
import gb.test.common.FakeData;
import gb.test.dto.CommentInputBuilder;
import gb.test.it.common.RecreatePerClassITCase;


public class CommentsApiValidationTests extends RecreatePerClassITCase {
    private static final String ANON_NAME = "anon";
    private static final String MESSAGE = "message";

    @Autowired
    private CommentsApi commentsApi;


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
        // Arrange.
        final String longMessage = FakeData.stringWithLength(
                Comment.MESSAGE_MAX_LENGTH);
        final CommentInput input = this.getCommentInputBuilder()
                .message(longMessage).build();

        // Act.
        final CommentEntry entry = this.commentsApi.createComment(input);

        // Assert.
        assertThat(entry.getId()).isNotNull();
    }


    private CommentInputBuilder getCommentInputBuilder() {
        return new CommentInputBuilder()
                .name(ANON_NAME).message(MESSAGE);
    }
}
