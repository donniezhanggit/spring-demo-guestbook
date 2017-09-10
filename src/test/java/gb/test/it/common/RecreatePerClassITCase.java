package gb.test.it.common;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;


@DirtiesContext(classMode=ClassMode.AFTER_CLASS)
public abstract class RecreatePerClassITCase extends BaseDbITCase {
    @Override
    protected void predefinedData() {}
}
