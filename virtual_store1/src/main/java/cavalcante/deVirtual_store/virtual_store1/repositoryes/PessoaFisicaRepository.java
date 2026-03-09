package cavalcante.deVirtual_store.virtual_store1.repositoryes;


import cavalcante.deVirtual_store.virtual_store1.models.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(pf) > 0 THEN true ELSE false END FROM PessoaFisica pf WHERE pf.cpf = :cpf")
    boolean existsByCpf(String cpf);

    @Query("SELECT n FROM PessoaFisica n WHERE n.nome LIKE %:nome%")
    List<PessoaFisica> onome(String nome);

    List<PessoaFisica> findByNome(String nome);

    List<PessoaFisica> findByCpf(String cpf);

    List<PessoaFisica> findByEmail(String email);






}