package demo.test.it.common;

import javax.annotation.PostConstruct;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import demo.config.GuestBookProfiles;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
@ActiveProfiles(profiles = {GuestBookProfiles.H2_INTEGRATION_TESTING})
public abstract class AbstractDbITCase {
    final private Logger logger = LoggerFactory
            .getLogger(AbstractDbITCase.class);

    @Autowired
    private InitOnceChecker initOnceChecker;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @PostConstruct
    private void runPostConstruct() {
        if(this.initOnceChecker.getAndSet()) return;

        logger.info("Setting up predefined data for "
                + getClass().getSimpleName());

        this.predefinedData();
    }


    protected abstract void predefinedData();
}
