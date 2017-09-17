package gb.test.it.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import gb.model.Comment;
import gb.model.CommentBuilder;
import gb.repos.CommentsRepository;
import gb.test.common.FakeData;
import gb.test.it.common.RecreatePerClassITCase;


public class CommentTests extends RecreatePerClassITCase {
    private static final Long MINIMAL_ID = 10000L;
    private static final Short MINIMAL_VERSION = 0;
    private static final String NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED = LocalDateTime.now();


    @Autowired
    CommentsRepository commentsRepo;


    @Override
    protected void predefinedData() {
        final Comment anonComment = this.buildAnonComment();

        this.commentsRepo.save(anonComment);
    }


    @Test
    public void A_new_comment_should_be_saved() {
        // Arrange.
        final Comment comment = this.buildAnonComment();

        // Act.
        final Comment actual = this.commentsRepo.save(comment);

        // Assert.
        this.assertNewAnonymousComment(actual);
    }


    @Test
    public void A_comment_without_message_should_throw_exception() {
        // Arrange.
        final Comment commentWithoutMessage = this.getCommentBuilder()
                .message(null).build();

        // Act and assert.
        thrown.expect(TransactionSystemException.class);
        this.commentsRepo.save(commentWithoutMessage);
    }


    @Test
    public void A_comment_with_max_message_should_persist() {
        // Arrange.
        final String longMessage = FakeData.stringWithLength(
                Comment.MESSAGE_MAX_LENGTH);
        final Comment comment = this.getCommentBuilder()
                .message(longMessage).build();

        // Act and assert.
        this.commentsRepo.save(comment);

    }


    @Test
    public void A_comment_with_too_long_message_should_throw_exception() {
        // Arrange.
        final String longMessage = FakeData.stringWithLength(
                Comment.MESSAGE_MAX_LENGTH+1);
        final Comment comment = this.getCommentBuilder()
                .message(longMessage).build();

        // Act and assert.
        thrown.expect(TransactionSystemException.class);
        this.commentsRepo.save(comment);
    }


    @Test
    public void A_comment_with_max_anon_name_should_persist() {
        // Arrange.
        final String longAnonName = FakeData.stringWithLength(
                Comment.NAME_MAX_LENGTH);
        final Comment comment = this.getCommentBuilder()
                .name(longAnonName).build();

        // Act and assert.
        this.commentsRepo.save(comment);
    }


    @Test
    public void A_comment_with_too_long_anon_name_should_throw_exception() {
        // Arrange.
        final String longAnonName = FakeData.stringWithLength(
                Comment.NAME_MAX_LENGTH+1);
        final Comment comment = this.getCommentBuilder()
                .name(longAnonName).build();

        // Act and assert.
        thrown.expect(TransactionSystemException.class);
        this.commentsRepo.save(comment);
    }


    @Test
    public void A_comment_without_created_date_should_throw_exception() {
        // Arrange.
        final Comment comment = this.getCommentBuilder()
                .created(null).build();

        // Act and assert.
        thrown.expect(TransactionSystemException.class);
        this.commentsRepo.save(comment);
    }


    // TODO: check user of comment!
    @Test
    public void Setters_and_getters_should_work_properly() {
        // Arrange.
        final String anotherName = "another name";
        final String anotherMessage = "another message";
        final LocalDateTime anotherCreated =
                LocalDateTime.of(2017, 8, 1, 12, 43, 17);

        // Fill with non-default values.
        final Comment input = this.getCommentBuilder()
                .name(anotherName).message(anotherMessage)
                .created(anotherCreated).build();

        // Revert to expected values.
        input.setName(NAME);
        input.setMessage(MESSAGE);
        input.setCreated(CREATED);

        // Act.
        final Comment actual = this.commentsRepo.save(input);

        // Assert.
        this.assertNewAnonymousComment(actual);
    }


    private void assertNewAnonymousComment(final Comment actual) {
        assertThat(actual.getUser()).isEqualTo(Optional.empty());

        this.assertComment(actual);
    }


    private void assertComment(final Comment actual) {
        assertThat(actual.getId()).isGreaterThanOrEqualTo(MINIMAL_ID);
        assertThat(actual.getVersion()).isEqualTo(MINIMAL_VERSION);
        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getMessage()).isEqualTo(MESSAGE);
        assertThat(actual.getCreated()).isEqualTo(CREATED);
    }


    private Comment buildAnonComment() {
        return this.getCommentBuilder().build();
    }


    private CommentBuilder getCommentBuilder() {
        return new CommentBuilder()
                .created(CREATED).name(NAME)
                .message(MESSAGE).user(null);
    }
}
