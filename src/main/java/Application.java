package demo;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages="demo.repos")
@EntityScan(basePackages="demo.models")
public class Application {
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(Application.class, args);
    }
}
