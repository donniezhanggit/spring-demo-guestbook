package gb.common.config;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@EnableCaching
public class MainConfig {
     private static final String JACKSON_DATE_TIME_FORMAT =
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    @Value("${spring.jackson.serialization.INDENT_OUTPUT:false}")
    private boolean prettyPrint;


    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();

        // Serialize LocalDateTime to format available for javascript.
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat(JACKSON_DATE_TIME_FORMAT));

        // Enable pretty print.
        if(this.prettyPrint) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        }

        log.debug("Configured ObjectMapper");

        return mapper;
    }
}
