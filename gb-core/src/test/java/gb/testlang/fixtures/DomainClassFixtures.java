package gb.testlang.fixtures;

import javax.annotation.Nonnull;

import org.springframework.test.util.ReflectionTestUtils;

import lombok.experimental.UtilityClass;


@UtilityClass
public class DomainClassFixtures {
    public void setId(@Nonnull final Object object, final Long id) {
        ReflectionTestUtils.setField(object, "id", id);
    }


    public void setVersion(@Nonnull final Object object, final Short version) {
        ReflectionTestUtils.setField(object, "version", version);
    }
}
