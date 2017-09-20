package gb.test.common;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public abstract class JUnitTestCase {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
}
