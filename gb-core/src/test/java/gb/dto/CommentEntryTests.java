package gb.dto;

import static gb.fixtures.CommentsFixtures.buildAnonComment;
import static gb.fixtures.CommentsFixtures.buildAnonCommentEntry;
import static gb.fixtures.CommentsFixtures.buildCommentFor;
import static gb.fixtures.CommentsFixtures.buildUserCommentEntry;
import static gb.fixtures.UsersFixtures.stubUser;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.model.Comment;


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
        final Comment comment = buildCommentFor(stubUser());
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
        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
        assertThat(actual.getUserName()).isEqualTo(expected.getUserName());
    }
}
