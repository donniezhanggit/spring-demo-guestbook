package gb.common.jackson;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gb.common.JUnitTestCase;
import gb.common.config.MainConfig;
import lombok.val;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE, makeFinal=true)
public class ObjectMapper_Java8DateTimeTests extends JUnitTestCase {
    private static final boolean PRETTY_PRINT = false;
    private static final String LOCAL_DATE = "2018-01-01";
    private static final String LOCAL_DATE_TIME_WITH_MILLIS =
            "2018-01-01T23:59:59.123";
    private static final String LOCAL_DATE_TIME_WITHOUT_MILLIS =
            "2018-01-01T23:59:59";
    private static final String ZONED_DATE_TIME =
            "2018-01-01T00:01:01+05:00";
    private static final String LOCAL_TIME_WITH_MILLIS = "20:30:31.123";
    private static final String LOCAL_TIME_WITHOUT_MILLIS = "20:30:31";


    ObjectMapper mapper = new MainConfig()
        .objectMapper(PRETTY_PRINT);


    @Test
    public void LocalTime_with_millis_serialized_as_ISO_string()
            throws Exception {
        // Arrange.
        val input = LocalTime.parse(LOCAL_TIME_WITH_MILLIS);
        val expected = "\"" + LOCAL_TIME_WITH_MILLIS + "\"";

        // Act.
        val actual = mapper.writeValueAsString(input);

        // Assert.
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void LocalTime_without_millis_serialized_as_ISO_string()
            throws Exception {
        // Arrange.
        val input = LocalTime.parse(LOCAL_TIME_WITHOUT_MILLIS);
        val expected = "\"" + LOCAL_TIME_WITHOUT_MILLIS + "\"";

        // Act.
        val actual = mapper.writeValueAsString(input);

        // Assert.
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void LocalDate_serialized_as_ISO_string()
            throws Exception {
        // Arrange.
        val input = LocalDate.parse(LOCAL_DATE);
        val expected = "\"" + LOCAL_DATE + "\"";

        // Act.
        val actual = mapper.writeValueAsString(input);

        // Assert.
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void LocalDateTime_with_millis_serialized_as_ISO_string()
            throws Exception {
        // Arrange.
        val input = LocalDateTime.parse(LOCAL_DATE_TIME_WITH_MILLIS);
        val expected = "\"" + LOCAL_DATE_TIME_WITH_MILLIS + "\"";

        // Act.
        val actual = mapper.writeValueAsString(input);

        // Assert.
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void LocalDateTime_without_millis_serialized_as_ISO_string()
            throws Exception {
        // Arrange.
        val input = LocalDateTime.parse(LOCAL_DATE_TIME_WITHOUT_MILLIS);
        val expected = "\"" + LOCAL_DATE_TIME_WITHOUT_MILLIS + "\"";

        // Act.
        val actual = mapper.writeValueAsString(input);

        // Assert.
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void DateTime_serialized_as_ISO_string() throws Exception {
        // Arrange.
        val input = ZonedDateTime.parse(ZONED_DATE_TIME);
        val expected = "\"" + ZONED_DATE_TIME + "\"";

        // Act.
        val actual = mapper.writeValueAsString(input);

        // Assert.
        assertThat(actual).isEqualTo(expected);
    }
}
