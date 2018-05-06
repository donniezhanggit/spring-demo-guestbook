package gb.test.it.endpoints;


import static gb.test.fixtures.CommentsFixtures.EXISTING_ID;
import static gb.test.fixtures.CommentsFixtures.buildAnonCommentEntry;
import static gb.test.fixtures.CommentsFixtures.buildAnonCommentInput;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import gb.api.CommentsApi;
import gb.controllers.MainController;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.test.it.common.EndpointITCase;


@WebMvcTest(MainController.class)
public class MainControllerTests extends EndpointITCase {
    private static final String ROOT_URL = "/";
    private static final String HTML_UTF8 = "text/html;charset=UTF-8";


    @MockBean
    private CommentsApi commentsApi;


    @Override
    public void setup() {
        final CommentEntry commentEntry = buildAnonCommentEntry();

        when(this.commentsApi.createComment(any(CommentInput.class)))
            .thenReturn(EXISTING_ID);
        when(this.commentsApi.getComments())
            .thenReturn(Arrays.asList(commentEntry));
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void Getting_a_list_of_comments()
            throws Exception {
        this.mockMvc.perform(get(ROOT_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(HTML_UTF8));
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void Creating_a_new_comment() throws Exception {
        final String jsonComment = jsonify(buildAnonCommentInput());

        this.mockMvc.perform(post(ROOT_URL)
                .content(jsonComment)
                .contentType(MediaType.TEXT_PLAIN))
            .andExpect(status().is2xxSuccessful());
    }
}
