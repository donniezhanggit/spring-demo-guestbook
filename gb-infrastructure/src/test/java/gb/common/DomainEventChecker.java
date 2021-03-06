package gb.common;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.hamcrest.Matcher;
import org.junit.Assert;

import gb.common.domain.BaseAggregateRoot;
import gb.common.events.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor(access=PRIVATE)
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class DomainEventChecker {
    Collection<DomainEvent> events;


    public UncastedDomainEventHolder hasOnlyOneEvent() {
        assertThat(events).hasSize(1);

        final DomainEvent event = events.iterator().next();

        return new UncastedDomainEventHolder(event);
    }


    public static DomainEventChecker
    checkThat(@Nullable final BaseAggregateRoot<?> root) {
        assertThat(root).isNotNull();

        return new DomainEventChecker(root.domainEvents());
    }


    @AllArgsConstructor
    @FieldDefaults(level=PRIVATE, makeFinal=true)
    public static class UncastedDomainEventHolder {
        DomainEvent uncastedEvent;


        @SuppressWarnings({"rawtypes", "unchecked"})
        public <T extends DomainEvent> DomainEventHolder<T>
        whichIsInstanceOf(@NonNull final Class<T> clazz) {
            assertThat(uncastedEvent).isInstanceOf(clazz);

            return new DomainEventHolder(clazz.cast(uncastedEvent));
        }
    }


    @AllArgsConstructor
    @FieldDefaults(level=PRIVATE, makeFinal=true)
    public static class DomainEventHolder<T extends DomainEvent> {
        T event;


        public <A> DomainEventHolder<T> hasFieldEquals(
                @NonNull final Function<? super T, ? extends A> accessor,
                @Nullable final A expected) {

            final A actual = accessor.apply(event);

            assertThat(actual).isEqualTo(expected);

            return this;
        }


        public <A> DomainEventHolder<T> hasFieldMatching(
                @NonNull final Function<? super T, ? extends A> accessor,
                @NonNull final Matcher<A> matcher) {

            final A actual = accessor.apply(event);

            Assert.assertThat(actual, matcher);

            return this;
        }
    }
}
