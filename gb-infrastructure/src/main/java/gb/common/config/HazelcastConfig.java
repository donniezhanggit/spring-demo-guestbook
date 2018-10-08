package gb.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;

import lombok.val;
import lombok.extern.log4j.Log4j2;


@Log4j2
@Configuration
public class HazelcastConfig {
    @Bean
    public Config hazelCastConfig(){
        val maxSizeConfig = new MaxSizeConfig(
                200,
                MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE
        );

        val mapConfig = new MapConfig()
                .setName("configuration")
                .setMaxSizeConfig(maxSizeConfig)
                .setEvictionPolicy(EvictionPolicy.LRU)
                .setTimeToLiveSeconds(-1);

        val config = new Config();

        config.setInstanceName("hazelcast-instance")
                .addMapConfig(mapConfig);

        log.info("hazelCastConfig was configured");

        return config;
    }
}
