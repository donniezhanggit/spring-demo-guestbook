package gb.test.it.api;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gb.api.CommentsApi;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.model.Comment;
import gb.model.CommentBuilder;
import gb.repos.CommentsRepository;
import gb.test.dto.CommentInputBuilder;
import gb.test.it.common.RecreatePerClassITCase;

import static org.assertj.core.api.Assertions.*;


public class CommentsApiTests extends RecreatePerClassITCase {
    private static final Long NON_EXISTENT_ID = -1L;
    private static final String ANON_NAME = "anon";
    private static final String MESSAGE = "message";
    private static final Short MIN_VERSION = 0;
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
    public void Comments_should_be_fetched() {
        // Arrange and act.
        final List<CommentEntry> actual = this.commentsApi.getComments();

        // Assert.
        assertThat(actual.size()).isGreaterThan(0);
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
    public void When_comment_isnt_exist_an_empty_optional_should_returned() {
        // Arrange and act.
        final Optional<CommentEntry> actual = this.commentsApi
                .getComment(NON_EXISTENT_ID);

        // Assert.
        assertThat(actual.isPresent()).isFalse();
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
    public void A_list_of_comments_should_be_ordered_by_date() {
        // Arrange and act.
        final List<LocalDateTime> dates = this.commentsApi.getComments()
                .stream().map(CommentEntry::getCreated)
                .collect(Collectors.toList());

        // Assert.
        assertThat(dates).isSorted();
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


    private List<Comment> buildCommentsList() {
        final CommentBuilder cb = new CommentBuilder()
                .name(ANON_NAME).message(MESSAGE);

        final Comment comment1 = cb.created(CREATED1).build();
        final Comment comment2 = cb.created(CREATED2).build();
        final Comment comment3 = cb.created(CREATED3).build();

        return Arrays.asList(comment1, comment2, comment3);
    }


    private List<CommentEntry> buildCommentEntriesList() {
        final List<Comment> comments = this.buildCommentsList();

        return comments.stream()
                .map(CommentEntry::from)
                .collect(Collectors.toList());
    }
}
