package gb.test.it.endpoints;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import gb.api.CommentsApi;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.test.dto.CommentEntryBuilder;
import gb.test.dto.CommentInputBuilder;
import gb.test.it.common.EndpointITCase;


public class CommentsControllerTests extends EndpointITCase {
    private static final long EXISTING_ID = 1L;
    private static final long NON_EXISTENT_ID = Long.MAX_VALUE;
    private static final short VERSION = 0;
    private static final String NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED = LocalDateTime.now();
    private static final String COMMENTS_API_URL = "/api/comments/";

    @MockBean
    private CommentsApi commentsApi;


    @Override
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final CommentEntry commentEntry = this.buildAnonCommentEntry();

        when(this.commentsApi.createComment(any(CommentInput.class)))
            .thenReturn(commentEntry);
        when(this.commentsApi.getComments())
            .thenReturn(Arrays.asList(commentEntry));
        when(this.commentsApi.getComment(EXISTING_ID))
            .thenReturn(Optional.of(commentEntry));
        when(this.commentsApi.getComment(NON_EXISTENT_ID))
            .thenReturn(Optional.empty());
    }


    @Test
    public void Getting_a_list_of_comments_should_return_200()
            throws Exception {
        this.mockMvc.perform(get(COMMENTS_API_URL))
            .andExpect(status().isOk())
            .andExpect(content()
                    .contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    public void Getting_a_list_of_comments_should_call_APIs_getComments()
            throws Exception {
        // Arrange and act.
        this.mockMvc.perform(get(COMMENTS_API_URL));

        // Assert.
        verify(this.commentsApi, times(1)).getComments();
        verifyNoMoreInteractions(this.commentsApi);
    }


    @Test
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
    public void Getting_a_non_existent_comment_should_return_404()
            throws Exception {
        // Arrange.
        final String url = COMMENTS_API_URL + NON_EXISTENT_ID;

        // Act and assert.
        this.mockMvc.perform(get(url))
            .andExpect(status().isNotFound());
    }


    @Test
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
    public void Creating_a_new_comment_should_return_201() throws Exception {
        // Arrange.
        final String jsonComment = this.jsonify(this.buildAnonCommentInput());

        // Act and assert.
        this.mockMvc.perform(post(COMMENTS_API_URL)
                .content(jsonComment)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated())
            .andExpect(content()
                    .contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    public void Creating_a_new_comment_should_call_APIs_createComment()
            throws Exception {
        // Arrange.
        final CommentInput input = this.buildAnonCommentInput();
        final String jsonComment = this.jsonify(input);

        // Act. 
        this.mockMvc.perform(post(COMMENTS_API_URL)
                .content(jsonComment)
                .contentType(MediaType.APPLICATION_JSON_UTF8));

        // Assert.
        verify(this.commentsApi, times(1))
            .createComment(any(CommentInput.class));
        verifyNoMoreInteractions(this.commentsApi);
    }


    private CommentEntry buildAnonCommentEntry() {
        return new CommentEntryBuilder()
                .id(EXISTING_ID).created(CREATED).version(VERSION)
                .anonName(NAME).message(MESSAGE).username(null)
                .build();
    }


    private CommentInput buildAnonCommentInput() {
        return new CommentInputBuilder()
                .name(NAME).message(MESSAGE).build();
    }
}
