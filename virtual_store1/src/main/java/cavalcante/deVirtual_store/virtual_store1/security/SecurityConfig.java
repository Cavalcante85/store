package cavalcante.deVirtual_store.virtual_store1.security;

import cavalcante.deVirtual_store.virtual_store1.services.ImplementacaoUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    private final ImplementacaoUserDetailsService implementacaoUserDetailsService;

    public SecurityConfig(ImplementacaoUserDetailsService implementacaoUserDetailsService) {
        this.implementacaoUserDetailsService = implementacaoUserDetailsService;
    }

    @Bean
  /*
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/salvarAcesso/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/deleteAcesso/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/login", "/login/**", "/home").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll() // Página de login personalizada
                        .defaultSuccessUrl("/home", true) // Redirecionamento pós-login
                )

                .csrf(csrf -> csrf.disable());

        return http.build();
    }

   */


    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para facilitar testes REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/virtual_store1/login").permitAll() // Permite acesso público ao login
                        .requestMatchers(HttpMethod.POST, "/salvarAcesso/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/deleteAcesso/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/login", "/login/**", "/home").permitAll()
                        .anyRequest().authenticated() // Requer autenticação para outras rotas
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // API sem estado

        return http.build();
    }









    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(implementacaoUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


