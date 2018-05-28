package gb.features.comments;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gb.common.ft.CucumberFTCase;


public class CommentsSteps extends CucumberFTCase {
  @Given("^you are in Given annotation$")
  public void given() throws Throwable {
  }

  @When("^you are in When annotation$")
  public void when() throws Throwable {
      executeGet("http://localhost:8080/api/comments/1");
  }

  @Then("^you are in Then annotation$")
  public void then() throws Throwable {
  }
}
