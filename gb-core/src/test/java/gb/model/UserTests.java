package gb.model;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.Test;

import gb.common.JUnitTestCase;

public class UserTests extends JUnitTestCase {
    @Test
    public void Null_builder_should_throw_NPE() {
        assertThatNullPointerException()
            .isThrownBy(() -> new User(null));
    }
}
