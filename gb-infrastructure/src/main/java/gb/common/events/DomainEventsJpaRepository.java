package gb.common.events;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import gb.common.data.jpa.JpaRepository;


@Repository
public interface DomainEventsJpaRepository
extends JpaRepository<PersistentDomainEvent, UUID> {
    List<PersistentDomainEvent>
    findAllByStatusOrderByCreatedAtDesc(DomainEventStatus status);
}
