/**
 *
 */
package gb.common.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Documented
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
@Service
@Validated
/**
 * This annotation is signaling that annotated class is an entry point
 * for an API.
 *
 * @author Whitesquall
 *
 */
public @interface Api {

}
