package gb.common.events.hibernate;

import static org.springframework.util.ReflectionUtils.findMethod;
import static org.springframework.util.ReflectionUtils.invokeMethod;
import static org.springframework.util.ReflectionUtils.makeAccessible;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gb.common.domain.BaseAggregateRoot;
import gb.common.events.DomainEvent;
import gb.common.events.EventPublisher;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AggregateRootInterceptor extends EmptyInterceptor {
    private static final long serialVersionUID = 1L;


    @Override
    public boolean onFlushDirty(
            final Object entity,
            final Serializable id,
            final Object[] currentState,
            final Object[] previousState,
            final String[] propertyNames,
            final Type[] types) {

        if(!(entity instanceof BaseAggregateRoot)) {
            return false;
        }

        final BaseAggregateRoot aggregate = (BaseAggregateRoot) entity;
        final Collection<DomainEvent> domainEvents = aggregate.domainEvents();

        if(domainEvents.isEmpty()) {
            return false;
        }

        final EventPublisher publisher = getPublisher();

        domainEvents.forEach(publisher::publishEvent);
        clearEvents(aggregate);

        return true;
    }


    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
    private void clearEvents(@NonNull final BaseAggregateRoot aggregate) {
        try {
            final Method clearEvents =
                    findMethod(aggregate.getClass(), "clearDomainEvents");

            makeAccessible(clearEvents);
            invokeMethod(clearEvents, aggregate);
        } catch (Exception e) { // NOSONAR
            log.error("Something is wrong! {}", e);
        }
    }


    private static EventPublisher getPublisher() {
        return EventPublisherObtainer.obtain();
    }
}
