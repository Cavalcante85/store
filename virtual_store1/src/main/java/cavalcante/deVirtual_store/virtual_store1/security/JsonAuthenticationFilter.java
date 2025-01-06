package cavalcante.deVirtual_store.virtual_store1.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if ("application/json".equalsIgnoreCase(request.getContentType())) {
            try {
                // Lendo credenciais do corpo da requisição JSON
                Map<String, String> credentials = objectMapper.readValue(request.getInputStream(), Map.class);
                String username = credentials.get("login");
                String password = credentials.get("senha");

                // Criando o token de autenticação
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

                // Configura detalhes adicionais no token
                setDetails(request, authRequest);

                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar JSON de autenticação", e);
            }
        }

        return super.attemptAuthentication(request, response);
    }
}
