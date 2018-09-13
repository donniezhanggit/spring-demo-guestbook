package gb.common.events;

import static gb.common.events.DomainEventStatus.PENDING;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import lombok.experimental.FieldDefaults;


@Service
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class DomainEventsRepository {
    @NonNull DomainEventsJpaRepository jpaEventsRepo;
    @NonNull ObjectMapper objectMapper;


    public PersistentDomainEvent get(final UUID id) {
        return jpaEventsRepo.getOne(id);
    }


    public DomainEvent rehydrate(final UUID id) {
        final PersistentDomainEvent event = get(id);

        return rehydrate(event);
    }


    public DomainEvent rehydrate(final PersistentDomainEvent event) {
        return objectify(event.getPayload());
    }


    public PersistentDomainEvent save(@NonNull final DomainEvent event) {
        final String eventJson = jsonify(event);
        val newEvent = PersistentDomainEvent.builder()
                .id(event.getId())
                .createdAt(event.getCreatedAt())
                .payload(eventJson)
                .build();

        return jpaEventsRepo.save(newEvent);
    }


    @SneakyThrows
    private String jsonify(@NonNull final DomainEvent event) {
        return objectMapper.writeValueAsString(event);
    }


    @SneakyThrows
    private DomainEvent objectify(@NonNull final String eventJson) {
        return objectMapper.readValue(eventJson, DomainEvent.class);
    }


    public List<DomainEvent> findPendingEvents() {
        return jpaEventsRepo.findAllByStatusOrderByCreatedAtDesc(PENDING)
                .stream()
                .map(this::rehydrate)
                .collect(Collectors.toList());
    }
}
