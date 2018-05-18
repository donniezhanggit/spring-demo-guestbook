package gb.common;

import static lombok.AccessLevel.PUBLIC;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PUBLIC, makeFinal=true)
public abstract class JUnitTestCase {
    @Rule
    ExpectedException thrown = ExpectedException.none();

    @Rule
    MockitoRule mockitoRule = MockitoJUnit.rule();
}
