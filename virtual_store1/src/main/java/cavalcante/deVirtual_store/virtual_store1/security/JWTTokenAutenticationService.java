package cavalcante.deVirtual_store.virtual_store1.security;

import cavalcante.deVirtual_store.virtual_store1.ApplicationContextLoad;
import cavalcante.deVirtual_store.virtual_store1.models.Usuario;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Date;

// Criar e retornar autenticacao
@Service
@Component
public class JWTTokenAutenticationService {


    // 11 dias em milisegundos
    private static final long EXPIRATION_TIME = 959990000;

    // Senha
    private static final String SECRET = "Cavalcante@123";

    // Prefixo
    private static final String TOKEN_PREFIX = "Bearer";


    private  static final String HEADER_STRING = "Authorization";


   // Gera o TOKEN
   public void addAuthentication(HttpServletResponse response, String username) throws Exception{

       System.out.println("TESTANDO TOKEN");

       String JWT = Jwts.builder()
               .setSubject(username)
               .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
               .signWith(SignatureAlgorithm.HS512, SECRET).compact();


       String token = TOKEN_PREFIX + " "+ JWT;

       response.addHeader(HEADER_STRING,token);

       //liberacaoCors(response);

       // Para testes no postman
       response.getWriter().write("{\"Authorization\": \"" + token + "\"}");

       System.out.println("Token gerado: " + token);

   }

   public UsernamePasswordAuthenticationToken getAthentication (HttpServletRequest request, HttpServletResponse response){
      String token = request.getHeader(HEADER_STRING);

      if (token != null){
          String tokenFormatado = token.replace(TOKEN_PREFIX,"").trim();


          String user = Jwts.parser()
                  .setSigningKey(SECRET)
                  .parseClaimsJws(tokenFormatado)
                  .getBody().getSubject();

          if (user != null){
           Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class).findUserByLogin(user);

           if (usuario != null){
               return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(),usuario.getAuthorities());
           }


          }
      }

       return null;
   }

      private void  liberacaoCors(HttpServletResponse response){

         if (response.getHeader("Access-control-allow-Origin") == null){
          response.addHeader("Access-control-allow-Origin","*");
         }

         if (response.getHeader("Access-control-allow-Headers") == null){
              response.addHeader("Access-control-allow-Headers","*");
         }

         if (response.getHeader("Access-control-Request-Headers") == null){
              response.addHeader("Access-control-Request-Headers","*");
         }

         if (response.getHeader("Access-control-allow-Methods") == null){
              response.addHeader("Access-control-allow-Methods","*");
         }

      }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // ou substitua "*" por URLs específicas
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter();
    }







}
