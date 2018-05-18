package gb.common.it;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import org.springframework.test.annotation.DirtiesContext;


@DirtiesContext(classMode=AFTER_EACH_TEST_METHOD)
public abstract class RecreatePerMethodITCase extends BaseDbITCase {
    @Override
    protected void predefinedData() {}
}
