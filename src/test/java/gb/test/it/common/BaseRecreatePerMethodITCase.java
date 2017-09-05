package gb.test.it.common;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;


@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class BaseRecreatePerMethodITCase extends AbstractDbITCase {

}
