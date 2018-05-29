package gb.common.config;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import lombok.val;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@EnableCaching
public class MainConfig {
    @Bean
    public ObjectMapper objectMapper(
            @Value("${spring.jackson.serialization.INDENT_OUTPUT:false}")
            final boolean prettyPrint) {
        val mapper = new ObjectMapper();

        // Show date/time as ISO8601 by default.
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.configure(SerializationFeature.INDENT_OUTPUT, prettyPrint);

        // Avoid having to annotate classes. Requires Java 8, pass -parameters
        // to javac and jackson-module-parameter-names as a dependency.
        mapper.registerModule(new ParameterNamesModule(PROPERTIES));

        // Make private fields of classes visible to Jackson.
        mapper.setVisibility(FIELD, ANY);

        // Ignore unknown fields.
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        log.info("Configuring of ObjectMapper finished");

        return mapper;
    }
}
