package cavalcante.deVirtual_store.virtual_store1.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/product/**").permitAll() // Ignorar autenticação para esse endpoint
                                .anyRequest().authenticated() // Proteger todas as outras requisições
                )
                .csrf(csrf -> csrf.disable()); // Desabilitar CSRF, se necessário

        return http.build();
    }
}