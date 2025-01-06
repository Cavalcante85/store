package cavalcante.deVirtual_store.virtual_store1.repositoryes;

import cavalcante.deVirtual_store.virtual_store1.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select a from Usuario a where a.login = ?1")
    Usuario findUserByLogin(String login);





}
