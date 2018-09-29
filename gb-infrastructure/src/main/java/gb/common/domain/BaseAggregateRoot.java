package gb.common.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gb.common.events.DomainEvent;
import gb.common.events.hibernate.AggregateRootInterceptor;
import lombok.NonNull;
import lombok.val;

/**
 * Convenience base class for aggregate roots.
 *
 * Exposes
 *  - {@link #registerEvent(DomainEvent)},
 *  - {@link #registerEventSupplier(Supplier)} and
 *  - {@link #registerEventProvider(EventProvider)}
 * to capture domain events and expose them via {@link #domainEvents())}.
 * The implementation is using the general event publication mechanism
 * implied by {@link DomainEvents} and {@link AfterDomainEventPublication}.
 *
 * If you want register event directly right now, then use
 * {@link #registerEvent(DomainEvent)}.
 * If you want register event lazily (events will be generated when a
 * method annotated with {@link DomainEvents} will be called), then use
 * {@link #registerEventSupplier(Supplier)} or
 * {@link #registerEventProvider(EventProvider)}
 *
 * @param <A> aggregate root class.
 */
@SuppressFBWarnings(value="SE_TRANSIENT_FIELD_NOT_RESTORED")
public abstract class BaseAggregateRoot<A extends BaseAggregateRoot<A>>
implements Serializable {
    private static final long serialVersionUID = 1L;

    private final transient @Transient
    Set<DomainEvent> domainEvents = new HashSet<>();

    private final transient @Transient
    Set<Supplier<DomainEvent>> eventSuppliers = new HashSet<>();


    /**
     * Registers the given event object for publication on a call to a
     * Spring Data repository's save methods.
     *
     * @param event must not be {@literal null}.
     */
    protected void registerEvent(@NonNull final DomainEvent event) {
        domainEvents.add(event);
    }


    /**
     * Register the given event object provider for later publication of
     * event.
     *
     * Can be useful when you need a way to get sequence style generated
     * ID or version in event. Using this method you will get a lazy event
     * registration during flushing when you will have ability to get ID
     * and version.
     *
     * Example with constructor and static factory method.
     * <pre>
     * {@code
     * class AggregateExample extends BaseAggregateRoot {
     *     final String name;
     *
     *     public AggregateExample(String name) {
     *         this.name = name;
     *
     *         registerEvent(() -> NewAggregateExampleCreated.of(this));
     *     }
     * }
     * }
     * </pre>
     *
     * @param eventProvider event provider which returns a new event.
     */
    protected void
    registerEventProvider(@NonNull final EventProvider<A> eventProvider) {
        @SuppressWarnings("unchecked")
        val provider = (EventProvider<BaseAggregateRoot<A>>) eventProvider;

        eventSuppliers.add(() -> provider.buildEventFromAggregate(this));
    }


    /**
     * Register the given event object supplier for later publication of
     * event.
     *
     * @param eventSupplier event supplier which returns a new event.
     */
    protected void
    registerEventSupplier(@NonNull final Supplier<DomainEvent> eventSupplier) {
        eventSuppliers.add(eventSupplier);
    }


    /**
     * Clears all domain events currently held. Usually invoked by the
     * infrastructure in place in Spring Data repositories.
     *
     * @see AggregateRootInterceptor
     */
    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        domainEvents.clear();
        eventSuppliers.clear();
    }


    /**
     * All domain events currently captured by the aggregate.
     */
    @DomainEvents
    public Collection<DomainEvent> domainEvents() {
        final Set<DomainEvent> lazyInitialized = eventSuppliers.stream()
                .map(Supplier::get)
                .collect(Collectors.toSet());

        final Set<DomainEvent> allEvents = Stream.concat(
                domainEvents.stream(),
                lazyInitialized.stream())
                .collect(Collectors.toSet());

        return Collections.unmodifiableSet(allEvents);
    }
}
