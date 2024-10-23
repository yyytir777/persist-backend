package yyytir777.persist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PersistApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersistApplication.class, args);
    }

}
