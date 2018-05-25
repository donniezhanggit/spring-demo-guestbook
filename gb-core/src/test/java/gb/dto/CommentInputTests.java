package gb.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import gb.common.JUnitTestCase;
import lombok.val;


public class CommentInputTests extends JUnitTestCase {
    @Test
    public void When_empty_method_called_should_produce_empty_input() {
        // Act.
        val commentInput = CommentInput.empty();

        // Assert.
        assertThat(commentInput.getAnonName()).isNull();
        assertThat(commentInput.getMessage()).isNull();
    }
}
