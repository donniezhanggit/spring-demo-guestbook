package demo.common;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;

import demo.config.GuestBookProfiles;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = {GuestBookProfiles.H2_INTEGRATION_TESTING})
public abstract class AbstractITCase {

    @BeforeTransaction
    public abstract void predefinedDataTx();
}
