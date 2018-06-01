package gb.testlang.assertions;

import static gb.testlang.fixtures.CommentsFixtures.ANON_NAME;
import static gb.testlang.fixtures.CommentsFixtures.MESSAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gb.dto.CommentEntry;
import gb.repos.CommentsRepository;
import lombok.experimental.FieldDefaults;


@Component
@FieldDefaults(level=PRIVATE)
public class CommentAssertions {
    @Autowired(required=false)
    CommentsRepository commentsRepo;


    public void assertCommentRemoved(long commentId) {
        assertThat(commentsRepo.existsById(commentId)).isFalse();
    }


    public static void assertCommentEntry(
            final CommentEntry expected, final CommentEntry actual) {
        assertThat(actual).isNotNull();
        assertThat(actual)
            .isEqualToIgnoringGivenFields(expected, "id", "version");
    }


    public static void assertAnonCommentEntry(
            final CommentEntry expected, final CommentEntry actual) {
        assertCommentEntry(expected, actual);

        assertThat(actual.getUser()).isNull();
    }


    public static void assertUserCommentEntry(
            final CommentEntry expected, final CommentEntry actual) {
        assertCommentEntry(expected, actual);

        assertThat(actual.getUser().getId())
            .isEqualTo(expected.getUser().getId());
        assertThat(actual.getUser().getUserName())
            .isEqualTo(expected.getUser().getUserName());
    }


    public static void assertCommentEntryIT(final CommentEntry actual) {
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getVersion()).isNotNull();
        assertThat(actual.getCreated()).isNotNull();
        assertThat(actual.getMessage()).isEqualTo(MESSAGE);
        assertThat(actual.getAnonName()).isEqualTo(ANON_NAME);
        assertThat(actual.getUser()).isNull();
    }
}
