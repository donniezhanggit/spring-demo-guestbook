package gb.dto;

import static gb.testlang.assertions.CommentAssertions.assertAnonCommentEntry;
import static gb.testlang.assertions.CommentAssertions.assertUserCommentEntry;
import static gb.testlang.fixtures.CommentsFixtures.buildAnonComment;
import static gb.testlang.fixtures.CommentsFixtures.buildAnonCommentEntry;
import static gb.testlang.fixtures.CommentsFixtures.buildCommentFor;
import static gb.testlang.fixtures.CommentsFixtures.buildUserCommentEntry;
import static gb.testlang.fixtures.UsersFixtures.stubUser;

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
        assertAnonCommentEntry(expected, actual);
    }


    @Test
    public void testUserCommentMapping() {
        // Arrange.
        final Comment comment = buildCommentFor(stubUser());
        final CommentEntry expected = buildUserCommentEntry();

        // Act.
        final CommentEntry actual = CommentEntry.from(comment);

        // Assert.
        assertUserCommentEntry(expected, actual);
    }
}
