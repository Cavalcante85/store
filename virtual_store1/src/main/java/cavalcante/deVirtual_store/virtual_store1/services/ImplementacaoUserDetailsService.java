package cavalcante.deVirtual_store.virtual_store1.services;


import cavalcante.deVirtual_store.virtual_store1.models.Usuario;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ImplementacaoUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Passando aqui   "+ username + " !!! ");
        Usuario usuario = usuarioRepository.findUserByLogin(username);
        System.out.println("Passando aqui 2!!!");

        if (usuario == null){
         throw new UsernameNotFoundException("Usuário nao encontrado!");
        }
        System.out.println("Passando aqui 3!!!");

        // Criando um objeto User (implementa UserDetails) com as informações do usuário
        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles("ADMIN") // Substituir por roles reais, se existirem
                .build();


    }

}



