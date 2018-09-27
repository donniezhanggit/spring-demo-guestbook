package gb.common.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gb.common.events.DomainEvent;
import lombok.NonNull;


@SuppressFBWarnings(value="SE_TRANSIENT_FIELD_NOT_RESTORED")
public abstract class BaseAggregateRoot
implements Serializable {
    private static final long serialVersionUID = 1L;

    private final transient @Transient Set<DomainEvent> domainEvents =
            new HashSet<>();

    private final transient @Transient Set<EventProvider> eventProviders =
            new HashSet<>();


    /**
     * Registers the given event object for publication on a call to a
     * Spring Data repository's save methods.
     *
     * @param event must not be {@literal null}.
     * @return the event that has been added.
     */
    protected void registerEvent(@NonNull final DomainEvent event) {
        domainEvents.add(event);
    }


    protected void registerEvent(@NonNull final EventProvider eventProvider) {
        eventProviders.add(eventProvider);
    }


    /**
     * Clears all domain events currently held. Usually invoked by the
     * infrastructure in place in Spring Data repositories.
     */
    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        domainEvents.clear();
    }


    /**
     * All domain events currently captured by the aggregate.
     */
    @DomainEvents
    public Collection<DomainEvent> domainEvents() {
        final Set<DomainEvent> lazyInitialized = eventProviders.stream()
                .map(EventProvider::buildEvent)
                .collect(Collectors.toSet());

        final Set<DomainEvent> allEvents = Stream.concat(
                domainEvents.stream(),
                lazyInitialized.stream())
                .collect(Collectors.toSet());

        return Collections.unmodifiableSet(allEvents);
    }
}
