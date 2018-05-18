package gb.common.it;

import static gb.common.config.GuestBookProfiles.H2_INTEGRATION_TESTING;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import gb.common.JUnitTestCase;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@FieldDefaults(level=PRIVATE)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=NONE)
@ActiveProfiles(H2_INTEGRATION_TESTING)
public abstract class BaseDbITCase extends JUnitTestCase {
    @Autowired
    InitOnceChecker initOnceChecker;

    @Autowired
    DelegateApi delegateApi;


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
