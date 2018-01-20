package gb.common.config;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Configuration
@EnableCaching
public class MainConfig {
    private static final Logger LOG = LoggerFactory
            .getLogger(MainConfig.class);

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

        LOG.info("Configuring of ObjectMapper finished");

        return mapper;
    }
}
