package gb.common.events.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Marks a type that it's a domain event type.
 *
 * Every persistent domain event should have this annotation.
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface PersistentDomainEvent {

}
