package cavalcante.deVirtual_store.virtual_store1.security;

import cavalcante.deVirtual_store.virtual_store1.services.ImplementacaoUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    private final ImplementacaoUserDetailsService implementacaoUserDetailsService;

    public SecurityConfig(ImplementacaoUserDetailsService implementacaoUserDetailsService) {
        this.implementacaoUserDetailsService = implementacaoUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                //  PÁGINAS PÚBLICAS
                                .requestMatchers(HttpMethod.GET, "/").permitAll()
                                .requestMatchers(HttpMethod.GET, "/index").permitAll()
                                .requestMatchers(HttpMethod.GET, "/categorias").permitAll()
                                .requestMatchers(HttpMethod.GET, "/categoria/cadastrar").permitAll()
                                .requestMatchers(HttpMethod.POST, "/categoria/salvar").permitAll()

                                //  RECURSOS ESTÁTICOS
                                .requestMatchers("/webjars/**").permitAll()
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/js/**").permitAll()
                                .requestMatchers("/images/**").permitAll()

                                //  PÁGINA DE LOGIN
                                .requestMatchers(HttpMethod.GET, "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers("/acesso-negado").permitAll()
                                .requestMatchers("/error").permitAll()

                                // ENDPOINTS EXISTENTES
                                .requestMatchers(HttpMethod.POST, "/salvarAcesso/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/salvarPessoaPj/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/salvarPessoaPf/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/deleteAcesso/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/retornoAcessoPorId/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/retornoAcessoDesc/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/consultacep/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/consultacep/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/consultaNome/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/consultaNome/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/consultaLikeNome/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/consultaLieNome/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/consultaCpf/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/consultaCpf/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/consultaCnpj/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/consultaCnpj/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/consultaCnpjReceita/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/consultaCnpjReceita/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/salvarCategoria/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/salvarCategoria/**").permitAll()

                                // ÁREAS PROTEGIDAS (exigem login)
                                .requestMatchers("/categoria/editar/**").authenticated()
                                .requestMatchers("/categoria/excluir/**").authenticated()
                                .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(handling -> handling
                        .accessDeniedPage("/acesso-negado")
                )


                // COMENTADO PARA EVITAR CONFLITO COM FORM LOGIN
                // .addFilterAt(jsonAuthenticationFilter(authManager(null)), UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable());

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

    // COMENTADO PARA EVITAR CONFLITO
/*
    @Bean
    public JsonAuthenticationFilter jsonAuthenticationFilter(AuthenticationManager authManager) throws Exception {
        JsonAuthenticationFilter filter = new JsonAuthenticationFilter();
        filter.setAuthenticationManager(authManager);
        filter.setFilterProcessesUrl("/login");
        return filter;
    }
  */
}