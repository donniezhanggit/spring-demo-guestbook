package gb.test.it.api;

import static gb.test.fixtures.CommentsFixtures.ANON_NAME;
import static gb.test.fixtures.CommentsFixtures.MESSAGE;
import static gb.test.fixtures.CommentsFixtures.commentInputBuilderWithNameAndMessage;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import gb.api.CommentsApi;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.repos.CommentsRepository;
import gb.test.fixtures.CommentsFixtures;
import gb.test.it.common.RecreatePerClassITCase;


public class CommentsApiTests extends RecreatePerClassITCase {
    @Autowired
    private CommentsFixtures commentFixtures;

    @Autowired
    private CommentsApi commentsApi;

    @Autowired
    private CommentsRepository commentRepo;


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
        final long commentId = this.commentFixtures.existingCommentId();

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
                .getComment(CommentsFixtures.NON_EXISTENT_ID);

        // Assert.
        assertThat(actual.isPresent()).isFalse();
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void A_new_comment_should_be_persisted() {
        // Arrange.
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                        .build();

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
        // Arrange.
        this.commentFixtures.savedCommentList();

        // Act.
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
        final long existingCommentId = this.commentFixtures
                        .savedComment().getId();

        // Act.
        this.commentsApi.removeComment(existingCommentId);

        // Assert.
        assertThatCommentRemoved(existingCommentId);
    }


    private void assertThatCommentRemoved(long commentId) {
        assertThat(this.commentRepo.existsById(commentId)).isFalse();
    }


    private void assertCommentEntry(final CommentEntry actual) {
        assertThat(actual).isNotNull();
        assertThat(actual.getCreated()).isNotNull();
        assertThat(actual.getMessage()).isEqualTo(MESSAGE);
        assertThat(actual.getAnonName()).isEqualTo(ANON_NAME);
        assertThat(actual.getUsername()).isNull();
    }
}
