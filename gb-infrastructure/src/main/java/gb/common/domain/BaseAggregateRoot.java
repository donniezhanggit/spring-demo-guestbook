package gb.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.util.Assert;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gb.common.events.DomainEvent;


@SuppressFBWarnings(value="SE_TRANSIENT_FIELD_NOT_RESTORED")
public abstract class BaseAggregateRoot
implements Serializable {
    private static final long serialVersionUID = 1L;

    private final transient @Transient List<Object> domainEvents =
            new ArrayList<>();


    /**
     * Registers the given event object for publication on a call to a
     * Spring Data repository's save methods.
     *
     * @param event must not be {@literal null}.
     * @return the event that has been added.
     */
    protected void registerEvent(DomainEvent event) {
        Assert.notNull(event, "Domain event must not be null!");

        this.domainEvents.add(event);
    }


    /**
     * Clears all domain events currently held. Usually invoked by the
     * infrastructure in place in Spring Data repositories.
     */
    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        this.domainEvents.clear();
    }


    /**
     * All domain events currently captured by the aggregate.
     */
    @DomainEvents
    public Collection<Object> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}
