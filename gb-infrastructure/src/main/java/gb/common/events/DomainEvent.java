package gb.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;


@JsonTypeInfo(use=Id.NAME, property="type")
public interface DomainEvent {
    UUID getId();
    LocalDateTime getCreatedAt();
}
