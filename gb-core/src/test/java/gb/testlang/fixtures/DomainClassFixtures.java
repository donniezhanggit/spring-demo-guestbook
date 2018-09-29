package gb.testlang.fixtures;

import static lombok.AccessLevel.NONE;
import static org.springframework.util.ReflectionUtils.findMethod;
import static org.springframework.util.ReflectionUtils.invokeMethod;
import static org.springframework.util.ReflectionUtils.makeAccessible;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.Assert;

import com.google.common.base.Supplier;

import gb.common.domain.BaseAggregateRoot;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access=NONE)
public final class DomainClassFixtures {
    private static final String CLEAR_EVENTS_METHOD_NAME = "clearDomainEvents";


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
    <T extends BaseAggregateRoot<?>> T clearDomainEvents(final T aggregate) {
        final Method clearEvents =
                findMethod(aggregate.getClass(), CLEAR_EVENTS_METHOD_NAME);

        Assert.notNull(clearEvents, "clearEvents must not be null");

        makeAccessible(clearEvents);
        invokeMethod(clearEvents, aggregate);

        return aggregate;
    }


    /**
     * Run aggregate supplier with cleaning of domain events after all.
     *
     * @param supplier a lambda to run
     * @return aggregate root with cleared collection of domain events.
     */
    public static <T extends BaseAggregateRoot<?>>
    T doIgnoringEvents(final Supplier<T> supplier) {
        final T aggregate = supplier.get();

        clearDomainEvents(aggregate);

        return aggregate;
    }
}
