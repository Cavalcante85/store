package cavalcante.deVirtual_store.virtual_store1.repositoryes;

import cavalcante.deVirtual_store.virtual_store1.models.Acesso;
import cavalcante.deVirtual_store.virtual_store1.models.Pessoa;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaFisica;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface PessoaRepository extends JpaRepository <Pessoa,Long>{

    @Query("select a from PessoaJuridica a where a.cnpj =?1")
    List<PessoaJuridica> buscarcnpjPj(String cnpj);

    @Query("select a from PessoaFisica a where a.email =?1")
    List<PessoaFisica> buscaEmail(String email);



}
