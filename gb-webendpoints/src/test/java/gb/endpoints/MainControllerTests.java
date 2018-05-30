package gb.endpoints;


import static gb.testlang.fixtures.CommentsFixtures.ANON_NAME_DIV_TEXT;
import static gb.testlang.fixtures.CommentsFixtures.EXISTING_ID;
import static gb.testlang.fixtures.CommentsFixtures.USERNAME_DIV_TEXT;
import static gb.testlang.fixtures.CommentsFixtures.buildAnonCommentInput;
import static gb.testlang.fixtures.CommentsFixtures.buildCommentEntriesList;
import static gb.testlang.fixtures.UsersFixtures.USERNAME;
import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import gb.api.CommentsApi;
import gb.common.it.EndpointITCase;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.dto.CommentInputBuilder;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE)
@WithMockUser(username=USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class MainControllerTests extends EndpointITCase {
    private static final String ROOT_URL = "/";
    private static final String LOGIN_URL = "/login";
    private static final String HTML_UTF8 = "text/html;charset=UTF-8";
    private static final String LENGTH_ERROR_MESSAGE =
            "length must be between";


    @MockBean
    CommentsApi commentsApi;


    @Override
    public void setup() {
        final List<CommentEntry> commentEntries = buildCommentEntriesList();

        when(commentsApi.createComment(any(CommentInput.class)))
            .thenReturn(EXISTING_ID);
        when(commentsApi.getComments())
            .thenReturn(commentEntries);
    }


    @Test
    public void Getting_a_list_of_comments()
            throws Exception {
        mockMvc.perform(get(ROOT_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(HTML_UTF8))
            .andExpect(content().string(containsString(ANON_NAME_DIV_TEXT)))
            .andExpect(content().string(containsString(USERNAME_DIV_TEXT)));
    }


    @Test
    public void Creating_a_new_comment() throws Exception {
        final String params = payload(buildAnonCommentInput());

        mockMvc.perform(post(ROOT_URL)
                .content(params)
                .contentType(APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().stringValues("Location", ROOT_URL));
    }


    @Test
    public void Invalid_form_should_return_main_page() throws Exception {
        final CommentInput invalidInput = new CommentInputBuilder()
                .anonName("").message("").build();
        final String params = payload(invalidInput);

        mockMvc.perform(post(ROOT_URL)
                .content(params)
                .contentType(APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(LENGTH_ERROR_MESSAGE)));
    }


    @Test
    public void A_login_page_should_return_200() throws Exception {
        mockMvc.perform(get(LOGIN_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(HTML_UTF8))
            .andExpect(content().string(containsString("Login")))
            .andExpect(content().string(containsString("Password")));
    }


    private String payload(final CommentInput input) {
        return String.format(
                "name=%s&message=%s",
                input.getAnonName(),
                input.getMessage()
        );
    }
}
