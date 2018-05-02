package gb.test.it.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import gb.api.CommentsApi;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.model.Comment;
import gb.model.CommentBuilder;
import gb.repos.CommentsRepository;
import gb.test.dto.CommentInputBuilder;
import gb.test.it.common.RecreatePerClassITCase;


public class CommentsApiTests extends RecreatePerClassITCase {
    private static final Long NON_EXISTENT_ID = Long.MAX_VALUE;
    private static final String ANON_NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED1 =
            LocalDateTime.of(2017, 9, 1, 12, 34, 16);
    private static final LocalDateTime CREATED2 =
            LocalDateTime.of(2017, 9, 1, 12, 33, 19);
    private static final LocalDateTime CREATED3 =
            LocalDateTime.of(2017, 9, 1, 12, 37, 31);

    @Autowired
    private CommentsApi commentsApi;

    @Autowired
    private CommentsRepository commentRepo;


    @Override
    public void predefinedData() {
        commentRepo.save(this.buildCommentsList());
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void Comments_should_be_fetched() {
        // Arrange and act.
        final List<CommentEntry> actual = this.commentsApi.getComments();

        // Assert.
        assertThat(actual.size()).isGreaterThan(0);
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
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
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void When_comment_isnt_exist_an_empty_optional_should_returned() {
        // Arrange and act.
        final Optional<CommentEntry> actual = this.commentsApi
                .getComment(NON_EXISTENT_ID);

        // Assert.
        assertThat(actual.isPresent()).isFalse();
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void A_new_comment_should_be_persisted() {
        // Arrange.
        final CommentInput input = this.getCommentInputBuilder().build();

        // Act.
        final long id = this.commentsApi.createComment(input);
        final Optional<CommentEntry> actual = this.commentsApi
                .getComment(id);

        // Assert.
        assertThat(actual.isPresent()).isTrue();
        actual.ifPresent(this::assertCommentEntry);
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void A_list_of_comments_should_be_ordered_by_date() {
        // Arrange and act.
        final List<LocalDateTime> dates = this.commentsApi.getComments()
                .stream().map(CommentEntry::getCreated)
                .collect(Collectors.toList());

        // Assert.
        assertThat(dates).isSorted();
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void A_comment_should_be_removed() {
        // Arrange.
        final long existingCommentId = this.savedComment().getId();

        // Act.
        this.commentsApi.removeComment(existingCommentId);

        // Assert.
        assertThatCommentRemoved(existingCommentId);
    }


    private void assertThatCommentRemoved(long commentId) {
        Optional<Comment> removedComment = this.commentRepo
                        .findOne(commentId);

        assertThat(removedComment.isPresent()).isFalse();
    }


    private void assertCommentEntry(final CommentEntry actual) {
        assertThat(actual).isNotNull();
        assertThat(actual.getCreated()).isNotNull();
        assertThat(actual.getMessage()).isEqualTo(MESSAGE);
        assertThat(actual.getAnonName()).isEqualTo(ANON_NAME);
        assertThat(actual.getUsername()).isNull();
    }


    private CommentInputBuilder getCommentInputBuilder() {
        return new CommentInputBuilder()
                .name(ANON_NAME).message(MESSAGE);
    }


    private List<Comment> buildCommentsList() {
        final CommentBuilder cb = this.withNameAndMessage();
        final Comment comment1 = cb.created(CREATED1).build();
        final Comment comment2 = cb.created(CREATED2).build();
        final Comment comment3 = cb.created(CREATED3).build();

        return Arrays.asList(comment1, comment2, comment3);
    }


    private Comment savedComment() {
        final CommentBuilder cb = this.withNameAndMessage();
        final Comment comment = cb.created(CREATED1).build();

        return this.commentRepo.save(comment);
    }


    private CommentBuilder withNameAndMessage() {
        return new CommentBuilder().name(ANON_NAME).message(MESSAGE);
    }
}
