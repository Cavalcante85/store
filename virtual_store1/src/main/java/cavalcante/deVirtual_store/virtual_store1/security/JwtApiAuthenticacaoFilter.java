package cavalcante.deVirtual_store.virtual_store1.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

// Filtro onde todas as requisiçoes Serao capturadas para autenticar
public class JwtApiAuthenticacaoFilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Estabelece autenticaçao do usuario
        Authentication authentication = new JWTTokenAutenticationService().getAthentication((HttpServletRequest) request, (HttpServletResponse) response);

        // Cria processo de autenticaçao para o spring security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request,response);

    }
}
