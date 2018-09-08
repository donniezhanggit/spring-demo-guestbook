package gb.common.events;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Service
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
class DomainEventPersister {
    @NonNull DomainEventsRepository domainEventsRepo;


    @EventListener
    public void persistDomainEvent(@NonNull final DomainEvent event) {
        domainEventsRepo.save(event);
    }
}
