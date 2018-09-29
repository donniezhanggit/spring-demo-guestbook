package gb.domain;

import static gb.testlang.fixtures.CommentsFixtures.ANON_NAME;
import static gb.testlang.fixtures.CommentsFixtures.filledCommentBuilder;
import static gb.testlang.fixtures.UsersFixtures.buildUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.Test;

import gb.common.JUnitTestCase;


public class NewCommentAddedTests extends JUnitTestCase {
    @Test
    public void
    An_instantiation_of_event_without_aggregate_should_throw_IAE() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> NewCommentAdded.of(null));
    }


    @Test
    public void An_event_maps_from_users_comment() {
        // Arrange.
        final User user = buildUser();
        final Comment newComment = filledCommentBuilder()
                .user(user).build();

        // Act.
        final NewCommentAdded event = NewCommentAdded.of(newComment);

        // Assert.
        assertThat(event.getAuthorName()).isEqualTo(user.getUserName());
        assertThat(event.getMessage()).isEqualTo(newComment.getMessage());
    }


    @Test
    public void An_event_maps_from_anon_comment() {
        // Arrange.
        final Comment newComment = filledCommentBuilder()
                .anonName(ANON_NAME).user(null).build();

        // Act.
        final NewCommentAdded event = NewCommentAdded.of(newComment);

        // Assert.
        assertThat(event.getAuthorName()).isEqualTo(ANON_NAME);
        assertThat(event.getMessage()).isEqualTo(newComment.getMessage());
    }
}
