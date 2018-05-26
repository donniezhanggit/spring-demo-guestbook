package gb.common.annotations;


import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


/**
 * Signaling that annotated class is an entry point for an API service layer.
 * This type of service is a good candidate for transaction processing,
 * caching, validation, permission checking. And designed with care to reuse
 * independent of end point protocol.
 *
 * @author Whitesquall
 *
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
@Service
@Validated
public @interface Api {

}
