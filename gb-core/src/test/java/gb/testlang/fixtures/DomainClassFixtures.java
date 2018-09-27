package gb.testlang.fixtures;

import static lombok.AccessLevel.NONE;
import static org.springframework.util.ReflectionUtils.findMethod;
import static org.springframework.util.ReflectionUtils.invokeMethod;
import static org.springframework.util.ReflectionUtils.makeAccessible;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import org.springframework.test.util.ReflectionTestUtils;

import com.google.common.base.Supplier;

import gb.common.domain.BaseAggregateRoot;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor(access=NONE)
public final class DomainClassFixtures {
    public static void setId(@Nonnull final Object object, final Long id) {
        ReflectionTestUtils.setField(object, "id", id);
    }


    public static void
    setVersion(@Nonnull final Object object, final Short version) {
        ReflectionTestUtils.setField(object, "version", version);
    }


    /**
     * Clear domain events of aggregate using reflection.
     *
     * @param aggregate which events of should be removed.
     * @return processed aggregate
     */
    public static
    <T extends BaseAggregateRoot> T clearDomainEvents(final T aggregate) {
        try {
            final Method clearEvents =
                    findMethod(aggregate.getClass(), "clearDomainEvents");

            makeAccessible(clearEvents);
            invokeMethod(clearEvents, aggregate);
        } catch (Exception e) { // NOSONAR
            log.error("Something is wrong! {}", e);
        }

        return aggregate;
    }


    /**
     * Run aggregate supplier with cleaning of domain events after all.
     *
     * @param supplier a lambda to run
     * @return aggregate root with cleared collection of domain events.
     */
    public static <T extends BaseAggregateRoot>
    T doIgnoringEvents(final Supplier<T> supplier) {
        final T aggregate = supplier.get();

        clearDomainEvents(aggregate);

        return aggregate;
    }
}
