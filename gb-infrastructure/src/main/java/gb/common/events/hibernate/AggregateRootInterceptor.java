package gb.common.events.hibernate;

import static org.springframework.util.ReflectionUtils.findMethod;
import static org.springframework.util.ReflectionUtils.invokeMethod;
import static org.springframework.util.ReflectionUtils.makeAccessible;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;

import org.hibernate.EmptyInterceptor;
import org.hibernate.event.spi.MergeEvent;
import org.hibernate.type.Type;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.repository.core.support.EventPublishingRepositoryProxyPostProcessor;
import org.springframework.util.Assert;

import gb.common.domain.BaseAggregateRoot;
import gb.common.events.DomainEvent;
import gb.common.events.EventPublisher;
import lombok.NonNull;
import lombok.val;


/**
 * An implementation of Hibernate interceptor for registering domain
 * events during session flushing.
 *
 * According JPA entity state model a managed entity does not require
 * manual call for merging. Much more, it's considered harmful.
 * But Spring Data only will register domain events if you call
 * repositories' save* methods for your entities, and this will produce
 * a {@link MergeEvent} in Hibernate internals.
 *
 * @see BaseAggregateRoot
 * @see AbstractAggregateRoot
 * @see EventPublishingRepositoryProxyPostProcessor
 * @see EventPublisherObtainer
 */
public class AggregateRootInterceptor extends EmptyInterceptor {
    private static final long serialVersionUID = 1L;

    private static final String METHOD_NAME = "clearDomainEvents";
    private static final Method CLEAR_EVENTS_CACHED_METHOD;


    static {
        CLEAR_EVENTS_CACHED_METHOD =
                findMethod(BaseAggregateRoot.class, METHOD_NAME);

        Assert.notNull(CLEAR_EVENTS_CACHED_METHOD,
                "CLEAR_EVENTS_CACHED_METHOD must not be null");

        makeAccessible(CLEAR_EVENTS_CACHED_METHOD);
    }


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

        val aggregate = (BaseAggregateRoot<?>) entity;
        final Collection<DomainEvent> domainEvents = aggregate.domainEvents();

        if(domainEvents.isEmpty()) {
            return false;
        }

        final EventPublisher publisher = getPublisher();

        domainEvents.forEach(publisher::publishEvent);
        clearEvents(aggregate);

        return true;
    }


    private static
    void clearEvents(@NonNull final BaseAggregateRoot<?> aggregate) {
        invokeMethod(CLEAR_EVENTS_CACHED_METHOD, aggregate);
    }


    private static EventPublisher getPublisher() {
        return EventPublisherObtainer.obtain();
    }
}
