package gb.common.events;

import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import gb.common.data.DataRepository;


@Repository
interface DomainEventsJpaRepository
extends DataRepository<PersistentDomainEvent, UUID> {
    Set<PersistentDomainEvent> findAllByStatus(DomainEventStatus status);
}
