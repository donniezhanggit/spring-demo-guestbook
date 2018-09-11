package gb.features.glue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gb.common.ft.CucumberFTCase;
import gb.dto.CommentInput;
import lombok.val;



public class CommentsSteps extends CucumberFTCase {
    private static final String COMMENTS_URL =
            "http://localhost:8080/api/comments/";


    private CommentInput.CommentInputBuilder commentInputBuilder;
    private ResultActions lastAction;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Given("a comment input")
    public void a_comment_input() {
        commentInputBuilder = CommentInput.builder();
    }


    @And("no anon name")
    public void and_no_anon_name() {
        commentInputBuilder.anonName(null);
    }


    @And("anon name is {string}")
    public void and_anon_name(final String anonName) {
        commentInputBuilder.anonName(anonName);
    }


    @And("message is {string}")
    public void and_message(final String message) {
        commentInputBuilder.message(message);
    }


    @And("no message")
    public void no_message() {
        commentInputBuilder.message(null);
    }


    @When("I submit the comment input")
    public void i_submit_the_comment_input() throws Exception {
        val commentInput = commentInputBuilder.build();

        lastAction = mockMvc.perform(post(COMMENTS_URL)
                .content(jsonify(commentInput))
                .contentType(APPLICATION_JSON_UTF8));
    }


    @Then("response has status CREATED")
    public void response_has_status_CREATED() throws Exception {
        lastAction.andExpect(status().isCreated());
    }


    @Then("response has status UNPROCESSABLE_ENTITY")
    public void response_has_status_precondition_failed() throws Exception {
        lastAction.andExpect(status().isUnprocessableEntity());
    }


    @Then("response has status BAD_REQUEST")
    public void response_has_status_bad_request() throws Exception {
        lastAction.andExpect(status().isBadRequest());
    }


    @Then("response body has ID of created comment")
    public void response_has_ID_of_created_comment() throws Exception {
        final String content = lastAction.andReturn()
                .getResponse().getContentAsString();

        assertThat(content).matches("\\d+");
    }


    @And("response body contains {string}")
    public void response_body_contans(final String substring)
            throws Exception {
        final String content = lastAction.andReturn()
                .getResponse().getContentAsString();

        assertThat(content).contains(substring);
    }
}
