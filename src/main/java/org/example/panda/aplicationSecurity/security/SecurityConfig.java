package org.example.panda.aplicationSecurity.security;

import jakarta.servlet.http.HttpServletResponse;
import org.example.panda.aplicationSecurity.services.IJWTUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Autowired
    private IJWTUtilityService jwtUtilityService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //desabilitamos el csrf:Cross site request forgery, que era la vulnerabilidad
        return http
                .csrf(csrf ->
                        csrf.disable()
                )
                .authorizeHttpRequests(authRequest ->
                                authRequest
                                        .requestMatchers("/auth/**").permitAll() //estas rutas ya son publicas, puede acceder cualquiera
                                        .anyRequest().authenticated() //para el resto de request tienen que estar autenticados.
                        //si queremos manejar roles y mas autorizaciones personalizadas iría en esta parte
                )
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JWTAuthorizationFilter(jwtUtilityService),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling->
                        exceptionHandling
                                .authenticationEntryPoint((request, response, authException) -> {
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");//para cuando intentemos acceder a una ruta protegida y no tengamos acceso nos devuelva un 401 y no un 203
                                }))
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
