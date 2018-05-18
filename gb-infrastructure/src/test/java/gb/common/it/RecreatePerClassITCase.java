package gb.common.it;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

import org.springframework.test.annotation.DirtiesContext;


@DirtiesContext(classMode=AFTER_CLASS)
public abstract class RecreatePerClassITCase extends BaseDbITCase {
    @Override
    protected void predefinedData() {}
}
