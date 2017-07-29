package demo.common;

import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;


@Commit
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class BaseRecreatePerMethodITCase extends AbstractITCase {

}
