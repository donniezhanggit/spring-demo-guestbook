package gb.testlang.fixtures;

import static lombok.AccessLevel.NONE;

import javax.annotation.Nonnull;

import org.springframework.test.util.ReflectionTestUtils;

import lombok.NoArgsConstructor;


@NoArgsConstructor(access=NONE)
public final class DomainClassFixtures {
    public static void setId(@Nonnull final Object object, final Long id) {
        ReflectionTestUtils.setField(object, "id", id);
    }


    public static void
    setVersion(@Nonnull final Object object, final Short version) {
        ReflectionTestUtils.setField(object, "version", version);
    }
}
