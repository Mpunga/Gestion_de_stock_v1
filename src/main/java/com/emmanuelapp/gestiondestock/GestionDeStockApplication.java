package com.emmanuelapp.gestiondestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan("SwaggerConfiguration")
public class GestionDeStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDeStockApplication.class, args);
    }

}
