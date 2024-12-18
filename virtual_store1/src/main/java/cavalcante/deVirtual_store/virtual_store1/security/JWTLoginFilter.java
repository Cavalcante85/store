package cavalcante.deVirtual_store.virtual_store1.security;


import cavalcante.deVirtual_store.virtual_store1.models.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    // Configurando o gerenciador de autenticaçao
    protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {

        // Obriga autenticar URL
        super(new AntPathRequestMatcher(url));

        // Gerenciador de autenticaçao
        setAuthenticationManager(authenticationManager);

    }



/*
    protected JWTLoginFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }
    protected JWTLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }
*/




    //Retorna o usurio o procesar autenticaçao
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        Usuario usuario = new ObjectMapper().readValue(request.getInputStream(),Usuario.class);

        //Retorna Login e senha
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(usuario.getLogin(),usuario.getSenha()));
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        try {
            new JWTTokenAutenticationService().addAuthentication(response,authResult.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
