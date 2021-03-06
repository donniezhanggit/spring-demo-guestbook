package gb.services;

import static gb.testlang.fixtures.CommentsFixtures.buildAnonCommentInput;
import static gb.testlang.fixtures.CommentsFixtures.buildCommentInputWithMessage;
import static gb.testlang.fixtures.CommentsFixtures.filledCommentInputBuilder;
import static gb.testlang.fixtures.UsersFixtures.buildUser;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.annotation.Nullable;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.domain.Comment;
import gb.domain.User;
import gb.dto.CommentInput;
import lombok.val;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE, makeFinal=true)
public class CommentMapperTests extends JUnitTestCase {
    CurrentPrincipalService currentPrincipalService =
            mock(CurrentPrincipalService.class);

    CommentMapper mapper = new CommentMapper(currentPrincipalService);


    @Test
    public void Mapper_without_dependency_throws_IAE() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new CommentMapper(null));
    }


    @Test
    public void Mapping_of_null_should_throw_IAE() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> mapper.from(null));
    }


    @Test
    public void Mapper_should_instantiate_a_new_Comment() {
        // Arrange.
        final CommentInput input = buildAnonCommentInput();

        // Act.
        final Comment newComment = mapper.from(input);

        // Assert.
        assertThat(newComment).isNotNull();
    }


    @Test
    public void Fields_of_input_should_be_mapped_proper() {
        // Arrange.
        final CommentInput input = buildAnonCommentInput();

        // Act.
        final Comment newComment = mapper.from(input);

        // Assert.
        assertAnonComment(newComment, input);
    }


    @Test
    public void An_anonymous_comment_maps_to_comment_without_user() {
        // Arrange.
        final CommentInput anonCommentInput = buildAnonCommentInput();
        stubCurrentUserToBeAnonymous();

        // Act.
        final Comment newComment = mapper.from(anonCommentInput);

        // Assert.
        assertThat(newComment.getUser().isPresent()).isFalse();
    }


    @Test
    public void A_CommentInput_of_logged_user_maps_to_Comment_with_user() {
        // Arrange.
        final CommentInput commentInputOfLoggedUser =
                buildCommentInputWithMessage();
        final User currentUser = stubCurrentUserAsLoggedUser();

        // Act.
        final Comment newComment = mapper.from(commentInputOfLoggedUser);

        // Assert.
        assertThat(newComment.getUser().orElse(null)).isSameAs(currentUser);
    }


    @Test
    public void A_CommentInput_of_logged_user_maps_ignoring_name() {
        // Arrange.
        final CommentInput commentInputWithName =
                        filledCommentInputBuilder().build();
        stubCurrentUserAsLoggedUser();

        // Act.
        final Comment newComment = mapper.from(commentInputWithName);

        // Assert.
        assertThat(newComment.getAnonName()).isNull();
    }


    private void assertAnonComment(final Comment actual,
            final CommentInput expected) {
        assertThat(actual.getAnonName()).isEqualTo(expected.getAnonName());
        assertThat(actual.getMessage()).isEqualTo(expected.getMessage());
    }


    private void stubCurrentUser(@Nullable User user) {
        when(currentPrincipalService.getCurrentUser())
            .thenReturn(Optional.ofNullable(user));
    }


    private void stubCurrentUserToBeAnonymous() {
        stubCurrentUser(null);
    }


    private User stubCurrentUserAsLoggedUser() {
        val user = buildUser();

        stubCurrentUser(user);

        return user;
    }
}
