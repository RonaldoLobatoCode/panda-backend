package org.example.panda.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override //rutas privadas
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**") //todas las urls
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowedHeaders("Origin","Content-Type","Accept","Authorization")
                .allowCredentials(true) //esto la diferencia de la ruta publica: aqui le decimos que tiene que pasar credenciales
                .maxAge(3600);

        registry.addMapping("/api/v1/auth/**") //esto y lo que venga detras que seria login y register
                .allowedOrigins("http://localhost:4200") //si le ponemos "*" le decimos que todas las rutas son permitidas
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS") //aqui igual con * le indicamos que puede acceder a todos los metodos
                .allowedHeaders("Origin","Content-Type","Accept","Authorization")
                .allowCredentials(false) //esto la diferencia de la ruta privada: aqui le decimos que NO tiene que pasar credenciales
                .maxAge(3600);
    }
}
