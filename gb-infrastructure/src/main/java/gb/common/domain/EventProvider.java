package gb.common.domain;

import gb.common.events.DomainEvent;


@FunctionalInterface
public interface EventProvider<A> {
    DomainEvent buildEventFromAggregate(A aggregate);
}
