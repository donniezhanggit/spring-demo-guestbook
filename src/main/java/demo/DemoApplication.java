package demo;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages="repos")
@EntityScan(basePackages="models")
public class DemoApplication {
	
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(DemoApplication.class, args);
    }
}
