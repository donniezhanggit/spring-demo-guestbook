package demo.common;

import javax.annotation.PostConstruct;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import demo.config.GuestBookProfiles;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = {GuestBookProfiles.H2_INTEGRATION_TESTING})
public abstract class AbstractITCase {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @PostConstruct
    private void runPostConstruct() {
        logger.info("Setting up predefined data. Running predefinedDataTx");

        this.predefinedDataTx();
    }

    protected abstract void predefinedDataTx();
}
