package gb.endpoints;

import static gb.fixtures.CommentsFixtures.EXISTING_ID;
import static gb.fixtures.CommentsFixtures.NON_EXISTENT_ID;
import static gb.fixtures.CommentsFixtures.buildAnonCommentEntry;
import static gb.fixtures.CommentsFixtures.buildAnonCommentInput;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import gb.api.CommentsApi;
import gb.common.it.EndpointITCase;
import gb.controllers.CommentsController;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;


@WebMvcTest(CommentsController.class)
@WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
public class CommentsControllerTests extends EndpointITCase {
    private static final String COMMENTS_API_URL = "/api/comments/";

    @MockBean
    private CommentsApi commentsApi;


    @Override
    public void setup() {
        final CommentEntry commentEntry = buildAnonCommentEntry();

        when(commentsApi.createComment(any(CommentInput.class)))
            .thenReturn(EXISTING_ID);
        when(commentsApi.getComments())
            .thenReturn(Arrays.asList(commentEntry));
        when(commentsApi.getComment(EXISTING_ID))
            .thenReturn(Optional.of(commentEntry));
        when(commentsApi.getComment(NON_EXISTENT_ID))
            .thenReturn(Optional.empty());
        doNothing().when(commentsApi).removeComment(EXISTING_ID);
    }


    @Test
    public void Getting_a_list_of_comments_should_return_200()
            throws Exception {
        mockMvc.perform(get(COMMENTS_API_URL))
            .andExpect(status().isOk())
            .andExpect(content()
                    .contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    public void Getting_a_list_of_comments_should_call_APIs_getComments()
            throws Exception {
        // Arrange and act.
        mockMvc.perform(get(COMMENTS_API_URL));

        // Assert.
        verify(commentsApi, times(1)).getComments();
        verifyNoMoreInteractions(commentsApi);
    }


    @Test
    public void Getting_an_existing_comment_should_return_200()
            throws Exception {
        // Arrange.
        final String url = COMMENTS_API_URL + EXISTING_ID;

        // Act and assert.
        mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(content()
                    .contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    public void Getting_an_existing_comment_should_call_APIs_getComment()
            throws Exception {
        // Arrange.
        final String url = COMMENTS_API_URL + EXISTING_ID;

        // Act.
        mockMvc.perform(get(url));

        // Assert.
        verify(commentsApi, times(1)).getComment(EXISTING_ID);
        verifyNoMoreInteractions(commentsApi);
    }


    @Test
    public void Getting_a_non_existent_comment_should_return_404()
            throws Exception {
        // Arrange.
        final String url = COMMENTS_API_URL + NON_EXISTENT_ID;

        // Act and assert.
        mockMvc.perform(get(url))
            .andExpect(status().isNotFound());
    }


    @Test
    public void Getting_a_non_existent_comment_should_call_APIs_getComment()
            throws Exception {
        // Arrange.
        final String url = COMMENTS_API_URL + NON_EXISTENT_ID;

        // Act.
        mockMvc.perform(get(url));

        // Assert.
        verify(commentsApi, times(1)).getComment(NON_EXISTENT_ID);
        verifyNoMoreInteractions(commentsApi);
    }


    @Test
    public void Creating_a_new_comment_should_return_201() throws Exception {
        // Arrange.
        final String jsonCommentInput = jsonify(buildAnonCommentInput());

        // Act and assert.
        mockMvc.perform(post(COMMENTS_API_URL)
                .content(jsonCommentInput)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated())
            .andExpect(content()
                    .contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    public void Creating_a_new_comment_should_call_APIs_createComment()
            throws Exception {
        // Arrange.
        final String jsonCommentInput = jsonify(buildAnonCommentInput());

        // Act.
        mockMvc.perform(post(COMMENTS_API_URL)
                .content(jsonCommentInput)
                .contentType(MediaType.APPLICATION_JSON_UTF8));

        // Assert.
        verify(commentsApi, times(1))
            .createComment(any(CommentInput.class));
        verifyNoMoreInteractions(commentsApi);
    }


    @Test
    public void Removing_a_comment_should_return_204() throws Exception {
        // Arrange.
        final String url = COMMENTS_API_URL + EXISTING_ID;

        // Act and assert.
        mockMvc.perform(delete(url))
                .andExpect(status().isNoContent());
    }


    @Test
    public void Removing_a_comment_should_call_APIs_removeComment()
            throws Exception {
        // Arrange.
        final String url = COMMENTS_API_URL + EXISTING_ID;

        // Act.
        mockMvc.perform(delete(url));

        // Assert.
        verify(commentsApi, times(1)).removeComment(EXISTING_ID);
        verifyNoMoreInteractions(commentsApi);
    }
}
