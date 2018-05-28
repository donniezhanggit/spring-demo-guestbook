package gb.common.ft;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import gb.common.config.MainConfig;


@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources",
        tags="not @ignore",
        glue="gb.features"
)
@ContextConfiguration(classes=MainConfig.class)
public class FunctionalTests {

}
