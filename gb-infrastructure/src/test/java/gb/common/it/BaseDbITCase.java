package gb.common.it;

import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import gb.common.JUnitTestCase;
import gb.common.config.GuestBookProfiles;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
@ActiveProfiles(profiles = {GuestBookProfiles.H2_INTEGRATION_TESTING})
public abstract class BaseDbITCase extends JUnitTestCase {
    @Autowired
    private InitOnceChecker initOnceChecker;

    @Autowired
    private DelegateApi delegateApi;


    @PostConstruct
    private void runPostConstruct() {
        if(initOnceChecker.getAndSetTrue()) return;

        log.info("Setting up predefined data for {}",
                getClass().getSimpleName());

        predefinedData();
    }


    protected <T> T withTransaction(final Supplier<T> supplier) {
        return delegateApi.doWithTransaction(supplier);
    }


    protected abstract void predefinedData();
}