package gb.common.events;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import gb.common.data.DataRepository;


@Repository
public interface DomainEventsJpaRepository
extends DataRepository<PersistentDomainEvent, UUID> {
    List<PersistentDomainEvent>
    findAllByStatusOrderByCreatedAtDesc(DomainEventStatus status);
}
