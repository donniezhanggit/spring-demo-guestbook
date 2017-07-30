package demo.test.common;

import javax.annotation.PostConstruct;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import demo.config.GuestBookProfiles;


@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = {GuestBookProfiles.PG_INTEGRATION_TESTING})
public abstract class AbstractITCase {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private DataInitializationChecker dataInitializationChecker;

    @PostConstruct
    private void runContextPostConstruct() {
        if(this.dataInitializationChecker.getAndSet()) return;
        
        logger.info("Setting up predefined data. Running predefinedData");

        this.predefinedData();
    }


    protected abstract void predefinedData();
}
