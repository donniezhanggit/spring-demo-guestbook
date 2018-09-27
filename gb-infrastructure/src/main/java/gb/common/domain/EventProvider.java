package gb.common.domain;

import gb.common.events.DomainEvent;


@FunctionalInterface
public interface EventProvider {
    DomainEvent buildEvent();
}
