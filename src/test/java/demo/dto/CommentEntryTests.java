package demo.dto;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import demo.model.Comment;
import demo.model.CommentBuilder;


public class CommentEntryTests {

    @Test
    public void testAnonymousCommentMapping() {
        // Arrange.
        final LocalDateTime created = LocalDateTime.now();
        final String name = "A test anon";
        final String message = "A test message";

        final Comment comment = new CommentBuilder()
                .created(created)
                .name(name)
                .message(message)
                .build();

        final CommentEntry expected = new CommentEntry();
        expected.setAnonName(name);
        expected.setCreated(created);
        expected.setMessage(message);

        // Act.
        final CommentEntry actual = CommentEntry.from(comment);

        // Assert.
        this.assertCommentEntry(expected, actual);
    }


    private void assertCommentEntry(
            final CommentEntry expected, final CommentEntry actual) {
        assertTrue(expected.getCreated().equals(actual.getCreated()));
        assertTrue(expected.getAnonName().equals(actual.getAnonName()));
        assertTrue(expected.getMessage().equals(actual.getMessage()));
        assertNull(actual.getUsername());
    }
}
