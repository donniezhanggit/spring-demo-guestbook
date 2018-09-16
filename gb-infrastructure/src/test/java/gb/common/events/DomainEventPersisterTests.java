package gb.common.events;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import gb.common.JUnitTestCase;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;


@FieldDefaults(level=PRIVATE)
public class DomainEventPersisterTests extends JUnitTestCase {
    public static final String NAME = "random name";


    DomainEventsRepository eventsRepo = mock(DomainEventsRepository.class);


    @Before
    public void setUp() {
        final PersistentDomainEvent event = buildPersistentDomainEvent();

        when(eventsRepo.save(any())).thenReturn(event);
    }


    @Test
    public void An_instantiation_without_dependency_should_throw_IAE() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new DomainEventPersister(null));
    }


    @Test
    public void Passing_null_as_event_should_throw_IAE() {
        // Arrange.
        final DomainEventPersister persister =
                new DomainEventPersister(eventsRepo);

        // Act and assert.
        assertThatIllegalArgumentException()
            .isThrownBy(() -> persister.persistDomainEvent(null));
    }


    @Test
    public void Event_should_be_delegated_to_repo_for_saving() {
        // Arrange.
        final TestEvent event = buildTestEvent();
        final DomainEventPersister persister =
                new DomainEventPersister(eventsRepo);

        // Act.
        persister.persistDomainEvent(event);

        // Assert.
        verify(eventsRepo, times(1)).save(eq(event));
        verifyNoMoreInteractions(eventsRepo);
    }


    private TestEvent buildTestEvent() {
        return TestEvent.builder()
                .name(NAME)
                .build();
    }


    private PersistentDomainEvent buildPersistentDomainEvent() {
        final String payload = "{" + TestEvent.NAME_FIELD +
                ": " + "\"" + NAME + "\"}";

        return PersistentDomainEvent.builder()
            .id(UUID.randomUUID())
            .payload(payload)
            .build();
    }


    @Value
    @Builder
    @FieldDefaults(level=PRIVATE, makeFinal=true)
    @EqualsAndHashCode(callSuper=true)
    @FieldNameConstants
    private static final class TestEvent extends BaseDomainEvent {
        String name;
    }
}
