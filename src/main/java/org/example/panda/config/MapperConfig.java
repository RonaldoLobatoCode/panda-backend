package org.example.panda.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean //creamos este bean para que lo registre en la fábrica de spring para luego poder inyectarlo
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
