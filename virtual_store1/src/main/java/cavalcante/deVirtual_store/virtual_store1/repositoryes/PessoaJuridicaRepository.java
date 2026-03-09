package cavalcante.deVirtual_store.virtual_store1.repositoryes;

import cavalcante.deVirtual_store.virtual_store1.models.Pessoa;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaFisica;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
     @Query(value = "SELECT CASE WHEN COUNT(pj) > 0 THEN true ELSE false END FROM PessoaJuridica pj WHERE pj.cnpj = :cnpj")
        boolean existsByCnpj(@Param("cnpj") String cnpj);


    List<PessoaJuridica> findByCnpj(String cnpj);



}

