package gb.config;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Configuration
public class MainConfig {
    private final String jacksonDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Value("${spring.jackson.serialization.INDENT_OUTPUT}")
    private boolean prettyPrint;


    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();

        // Serialize LocalDateTime to format available for javascript.
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat(this.jacksonDateFormat));

        // Enable pretty print.
        if(this.prettyPrint) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        }

        return mapper;
    }
}
