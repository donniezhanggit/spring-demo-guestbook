package gb.api;

import static gb.testlang.fixtures.CommentsFixtures.NON_EXISTENT_ID;
import static gb.testlang.fixtures.CommentsFixtures.commentInputBuilderWithNameAndMessage;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import gb.common.it.RecreatePerClassITCase;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.testlang.assertions.CommentAssertions;
import gb.testlang.fixtures.CommentsFixtures;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE)
@WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
public class CommentsApiTests extends RecreatePerClassITCase {
    @Autowired
    CommentsFixtures commentFixtures;

    @Autowired
    CommentsApi commentsApi;

    @Autowired
    CommentAssertions assertions;


    @Test
    public void Comments_should_be_fetched() {
        // Arrange.
        commentFixtures.savedCommentList();

        // Act.
        final List<CommentEntry> actual = commentsApi.getComments();

        // Assert.
        assertThat(actual.size()).isGreaterThan(0);
    }


    @Test
    public void A_comment_by_id_should_be_fetched() {
        // Arrange.
        final long commentId = commentFixtures.existingCommentId();

        // Act.
        final Optional<CommentEntry> comment = commentsApi
                .getComment(commentId);

        // Assert.
        assertThat(comment.isPresent()).isTrue();
        comment.ifPresent(CommentAssertions::assertCommentEntryIT);
    }


    @Test
    public void When_comment_isnt_exist_an_empty_optional_should_returned() {
        // Arrange and act.
        final Optional<CommentEntry> actual = commentsApi
                .getComment(NON_EXISTENT_ID);

        // Assert.
        assertThat(actual.isPresent()).isFalse();
    }


    @Test
    public void A_new_comment_should_be_persisted() {
        // Arrange.
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                        .build();

        // Act.
        final long id = commentsApi.createComment(input);
        final Optional<CommentEntry> actual = commentsApi.getComment(id);

        // Assert.
        assertThat(actual.isPresent()).isTrue();
        actual.ifPresent(CommentAssertions::assertCommentEntryIT);
    }


    @Test
    public void A_list_of_comments_should_be_ordered_by_date() {
        // Arrange.
        commentFixtures.savedCommentList();

        // Act.
        final List<LocalDateTime> dates = commentsApi.getComments()
                .stream().map(CommentEntry::getCreated)
                .collect(Collectors.toList());

        // Assert.
        assertThat(dates).isSorted();
    }


    @Test
    public void A_comment_should_be_removed() {
        // Arrange.
        final long existingCommentId = commentFixtures
                        .savedComment().getId();

        // Act.
        commentsApi.removeComment(existingCommentId);

        // Assert.
        assertions.assertCommentRemoved(existingCommentId);
    }


    @Test
    public void A_removing_of_a_non_existent_comment_should_not_throw() {
        // Act.
        commentsApi.removeComment(NON_EXISTENT_ID);
    }
}
