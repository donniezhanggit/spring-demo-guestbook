package demo.dto;

import static org.assertj.core.api.Assertions.*;

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
        assertThat(expected.getCreated())
            .isEqualByComparingTo(actual.getCreated());
        assertThat(expected.getAnonName()).isEqualTo(actual.getAnonName());
        assertThat(expected.getMessage()).isEqualTo(actual.getMessage());
        assertThat(actual.getUsername()).isNull();
    }
}
