package gb.dto;

import static gb.testlang.assertions.CommentAssertions.assertAnonCommentEntry;
import static gb.testlang.assertions.CommentAssertions.assertUserCommentEntry;
import static gb.testlang.fixtures.CommentsFixtures.buildAnonComment;
import static gb.testlang.fixtures.CommentsFixtures.buildAnonCommentEntry;
import static gb.testlang.fixtures.CommentsFixtures.buildCommentFor;
import static gb.testlang.fixtures.CommentsFixtures.buildUserCommentEntry;
import static gb.testlang.fixtures.UsersFixtures.stubUser;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.model.Comment;


public class CommentEntryTests extends JUnitTestCase {
    @Test
    public void An_anonymous_Comment_should_map_to_CommentEntry() {
        // Arrange.
        final Comment comment = buildAnonComment();
        final CommentEntry expected = buildAnonCommentEntry();

        // Act.
        final CommentEntry actual = CommentEntry.from(comment);

        // Assert.
        assertAnonCommentEntry(expected, actual);
    }


    @Test
    public void A_user_Comment_should_map_to_CommentEntry() {
        // Arrange.
        final Comment comment = buildCommentFor(stubUser());
        final CommentEntry expected = buildUserCommentEntry();

        // Act.
        final CommentEntry actual = CommentEntry.from(comment);

        // Assert.
        assertUserCommentEntry(expected, actual);
    }


    @Test
    public void Mapping_of_null_should_throw_NPE() {
        assertThatNullPointerException()
            .isThrownBy(() -> CommentEntry.from(null));
    }
}
