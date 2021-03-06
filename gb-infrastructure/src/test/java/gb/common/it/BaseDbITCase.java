package gb.common.it;

import static gb.common.config.GuestBookProfiles.H2_INTEGRATION_TESTING;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.validation.ValidationException;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import gb.common.JUnitTestCase;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import net.ttddyy.dsproxy.QueryCountHolder;
import net.ttddyy.dsproxy.asserts.ProxyTestDataSource;


@Log4j2
@FieldDefaults(level=PRIVATE)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=NONE)
@ActiveProfiles(H2_INTEGRATION_TESTING)
public abstract class BaseDbITCase extends JUnitTestCase {
    @Autowired
    InitOnceChecker initOnceChecker;

    @Autowired
    TransactionRunner tr;

    @Autowired
    ProxyTestDataSourceHolder holder;


    @Before
    public void prepareDataSource() {
        getDs().reset();
        QueryCountHolder.clear();
    }


    @PostConstruct
    private void runPostConstruct() {
        if(initOnceChecker.getAndSetTrue()) return;

        log.info("Setting up predefined data for {}",
                getClass().getSimpleName());

        predefinedData();
    }


    protected <T> T withTransaction(final Supplier<T> supplier) {
        return tr.doInTransaction(supplier);
    }


    protected void assertThatInvalid(final ThrowingCallable throwingCallable) {
        assertThatExceptionOfType(ValidationException.class)
            .isThrownBy(throwingCallable);
    }


    protected ProxyTestDataSource getDs() {
        return holder.getTestDataSource();
    }


    protected abstract void predefinedData();
}
