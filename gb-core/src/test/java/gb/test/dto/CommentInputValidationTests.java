package gb.test.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Test;

import gb.dto.CommentInput;
import gb.model.Comment;
import gb.test.common.BeanValidationTestCase;
import gb.test.common.FakeData;


public class CommentInputValidationTests extends BeanValidationTestCase {
    private static final String ANON_NAME = "anon";
    private static final String MESSAGE = "message";


    @Test
    public void When_message_is_too_long_expect_validation_error() {
        // Arrange.
        final String tooLongMessage = FakeData.stringWithLength(
                Comment.MESSAGE_MAX_LENGTH+1);
        final CommentInput input = this.getCommentInputBuilder()
                .message(tooLongMessage).build();

        // Act.
        Set<ConstraintViolation<CommentInput>> errors = validate(input);

        // Assert.
        assertThat(errors).hasSize(1);
    }


    @Test
    public void When_message_is_max_expect_no_validation_errors() {
        // Arrange.
        final String longMessage = FakeData.stringWithLength(
                Comment.MESSAGE_MAX_LENGTH);
        final CommentInput input = this.getCommentInputBuilder()
                .message(longMessage).build();

        // Act.
        Set<ConstraintViolation<CommentInput>> errors = validate(input);

        // Assert.
        assertThat(errors).hasSize(0);
    }


    @Test
    public void When_name_is_too_long_expect_validation_error() {
        // Arrange.
        final String tooLongName = FakeData.stringWithLength(
                Comment.NAME_MAX_LENGTH+1);
        final CommentInput input = this.getCommentInputBuilder()
                .name(tooLongName).build();

        // Act.
        Set<ConstraintViolation<CommentInput>> errors = validate(input);

        // Assert.
        assertThat(errors).hasSize(1);
    }


    @Test
    public void When_name_is_max_expect_no_validation_errors() {
        // Arrange.
        final String longName = FakeData.stringWithLength(
                Comment.NAME_MAX_LENGTH);
        final CommentInput input = this.getCommentInputBuilder()
                .message(longName).build();

        // Act.
        Set<ConstraintViolation<CommentInput>> errors = validate(input);

        // Assert.
        assertThat(errors).hasSize(0);
    }




    @Test
    public void When_name_is_null_expect_validation_error() {
        // Arrange.
        final CommentInput input = this.getCommentInputBuilder()
                .name(null).build();

        // Act.
        Set<ConstraintViolation<CommentInput>> errors = validate(input);

        // Assert.
        assertThat(errors).hasSize(1);
    }


    @Test
    public void When_message_is_null_expect_validation_error() {
        // Arrange.
        final CommentInput input = this.getCommentInputBuilder()
                .message(null).build();

        // Act.
        Set<ConstraintViolation<CommentInput>> errors = validate(input);

        // Assert.
        assertThat(errors).hasSize(1);
    }


    private CommentInputBuilder getCommentInputBuilder() {
        return new CommentInputBuilder()
                .name(ANON_NAME).message(MESSAGE);
    }
}
