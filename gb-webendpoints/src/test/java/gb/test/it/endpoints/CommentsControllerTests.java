package gb.test.it.endpoints;

import static gb.test.fixtures.CommentsFixtures.EXISTING_ID;
import static gb.test.fixtures.CommentsFixtures.NON_EXISTENT_ID;
import static gb.test.fixtures.CommentsFixtures.buildAnonCommentEntry;
import static gb.test.fixtures.CommentsFixtures.buildAnonCommentInput;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
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
import gb.controllers.CommentsController;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.test.it.common.EndpointITCase;


@WebMvcTest(CommentsController.class)
public class CommentsControllerTests extends EndpointITCase {
    private static final String COMMENTS_API_URL = "/api/comments/";

    @MockBean
    private CommentsApi commentsApi;


    @Override
    public void setup() {
        final CommentEntry commentEntry = buildAnonCommentEntry();

        when(this.commentsApi.createComment(any(CommentInput.class)))
            .thenReturn(EXISTING_ID);
        when(this.commentsApi.getComments())
            .thenReturn(Arrays.asList(commentEntry));
        when(this.commentsApi.getComment(EXISTING_ID))
            .thenReturn(Optional.of(commentEntry));
        when(this.commentsApi.getComment(NON_EXISTENT_ID))
            .thenReturn(Optional.empty());
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void Getting_a_list_of_comments_should_return_200()
            throws Exception {
        this.mockMvc.perform(get(COMMENTS_API_URL))
            .andExpect(status().isOk())
            .andExpect(content()
                    .contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void Getting_a_list_of_comments_should_call_APIs_getComments()
            throws Exception {
        // Arrange and act.
        this.mockMvc.perform(get(COMMENTS_API_URL));

        // Assert.
        verify(this.commentsApi, times(1)).getComments();
        verifyNoMoreInteractions(this.commentsApi);
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void Getting_an_existing_comment_should_return_200()
            throws Exception {
        // Arrange.
        final String url = COMMENTS_API_URL + EXISTING_ID;

        // Act and assert.
        this.mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(content()
                    .contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void Getting_an_existing_comment_should_call_APIs_getComment()
            throws Exception {
        // Arrange.
        final String url = COMMENTS_API_URL + EXISTING_ID;

        // Act.
        this.mockMvc.perform(get(url));

        // Assert.
        verify(this.commentsApi, times(1)).getComment(EXISTING_ID);
        verifyNoMoreInteractions(this.commentsApi);
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void Getting_a_non_existent_comment_should_return_404()
            throws Exception {
        // Arrange.
        final String url = COMMENTS_API_URL + NON_EXISTENT_ID;

        // Act and assert.
        this.mockMvc.perform(get(url))
            .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void Getting_a_non_existent_comment_should_call_APIs_getComment()
            throws Exception {
        // Arrange.
        final String url = COMMENTS_API_URL + NON_EXISTENT_ID;

        // Act.
        this.mockMvc.perform(get(url));

        // Assert.
        verify(this.commentsApi, times(1)).getComment(NON_EXISTENT_ID);
        verifyNoMoreInteractions(this.commentsApi);
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void Creating_a_new_comment_should_return_201() throws Exception {
        // Arrange.
        final String jsonCommentInput = jsonify(buildAnonCommentInput());

        // Act and assert.
        this.mockMvc.perform(post(COMMENTS_API_URL)
                .content(jsonCommentInput)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated())
            .andExpect(content()
                    .contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void Creating_a_new_comment_should_call_APIs_createComment()
            throws Exception {
        // Arrange.
        final String jsonCommentInput = jsonify(buildAnonCommentInput());

        // Act.
        this.mockMvc.perform(post(COMMENTS_API_URL)
                .content(jsonCommentInput)
                .contentType(MediaType.APPLICATION_JSON_UTF8));

        // Assert.
        verify(this.commentsApi, times(1))
            .createComment(any(CommentInput.class));
        verifyNoMoreInteractions(this.commentsApi);
    }
}
