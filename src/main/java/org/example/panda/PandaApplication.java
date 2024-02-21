package org.example.panda;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableFeignClients("org.example.panda") //habilidamos el feignclients
@ImportAutoConfiguration({FeignAutoConfiguration.class}) //importe la autoconfiguracion necesaria para utilizar feign
@OpenAPIDefinition //importante para la documentacion con swagger
public class PandaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PandaApplication.class, args);
    }

}
