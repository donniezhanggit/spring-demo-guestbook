package gb.test.dto;

import static gb.test.fixtures.CommentsFixtures.buildAnonComment;
import static gb.test.fixtures.CommentsFixtures.buildAnonCommentEntry;
import static gb.test.fixtures.CommentsFixtures.buildUserComment;
import static gb.test.fixtures.CommentsFixtures.buildUserCommentEntry;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import gb.dto.CommentEntry;
import gb.model.Comment;
import gb.test.common.JUnitTestCase;


public class CommentEntryTests extends JUnitTestCase {
    @Test
    public void testAnonymousCommentMapping() {
        // Arrange.
        final Comment comment = buildAnonComment();
        final CommentEntry expected = buildAnonCommentEntry();

        // Act.
        final CommentEntry actual = CommentEntry.from(comment);

        // Assert.
        assertCommentEntry(expected, actual);
    }


    @Test
    public void testUserCommentMapping() {
        // Arrange.
        final Comment comment = buildUserComment();
        final CommentEntry expected = buildUserCommentEntry();

        // Act.
        final CommentEntry actual = CommentEntry.from(comment);

        // Assert.
        assertCommentEntry(expected, actual);
    }


    private void assertCommentEntry(
            final CommentEntry expected, final CommentEntry actual) {
        assertThat(actual.getCreated()).isEqualTo(expected.getCreated());
        assertThat(actual.getAnonName()).isEqualTo(expected.getAnonName());
        assertThat(actual.getMessage()).isEqualTo(expected.getMessage());
        assertThat(actual.getUsername()).isEqualTo(expected.getUsername());
    }

}
