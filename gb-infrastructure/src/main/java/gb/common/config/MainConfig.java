package gb.common.config;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.val;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@EnableCaching
public class MainConfig {
    private static final String ISO_DATETIME_WITHOUT_TIMEZONE =
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    @Value("${spring.jackson.serialization.INDENT_OUTPUT:false}")
    private boolean prettyPrint;


    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        val dateFormat = new SimpleDateFormat(ISO_DATETIME_WITHOUT_TIMEZONE);

        // Serialize LocalDateTime to format available for javascript.
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(dateFormat);

        // Enable pretty print.
        if(prettyPrint) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        }

        log.info("Configuring of ObjectMapper finished");

        return mapper;
    }
}
