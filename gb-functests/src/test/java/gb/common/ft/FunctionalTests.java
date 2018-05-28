package gb.common.ft;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources",
        tags="not @ignore",
        glue="gb.features"
)
public class FunctionalTests {

}
