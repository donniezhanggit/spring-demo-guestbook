package demo.test.common;

import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;


@Commit
@DirtiesContext(classMode=ClassMode.AFTER_CLASS)
public abstract class BaseRecreatePerClassITCase extends AbstractITCase {

}
