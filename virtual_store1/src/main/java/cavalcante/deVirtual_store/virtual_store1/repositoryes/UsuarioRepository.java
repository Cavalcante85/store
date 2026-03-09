package cavalcante.deVirtual_store.virtual_store1.repositoryes;

import cavalcante.deVirtual_store.virtual_store1.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select a from Usuario a where a.login = ?1")
    Usuario findUserByLogin(String login);


    @Query("SELECT u FROM Usuario u WHERE u.pessoa.id = ?1 AND u.login = ?2")
    Usuario findUserByPessoa(Long pessoaId, String email);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into tb_usuarios_acesso(usuario_id, acesso_id) " +
                                       "values(?1, (select id from tb_acesso where descricao = 'ROLE_USER'))")
    void insereAcessoUserPj(long id);

    @Query(nativeQuery = true,value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'tb_usuarios_acesso' and column_name = 'acesso_id' and constraint_name <> 'unique_acesso_user' ")
    String consultaconstraintAcesso();
}
