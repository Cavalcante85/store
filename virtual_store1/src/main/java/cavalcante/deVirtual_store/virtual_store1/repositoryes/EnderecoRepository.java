package cavalcante.deVirtual_store.virtual_store1.repositoryes;

import cavalcante.deVirtual_store.virtual_store1.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    // Buscar por CEP
    List<Endereco> findByCepContaining(String cep);

    // Buscar por cidade
    List<Endereco> findByCidadeContainingIgnoreCase(String cidade);

    // Buscar por UF
    List<Endereco> findByUf(String uf);

    // Buscar por bairro
    List<Endereco> findByBairroContainingIgnoreCase(String bairro);

    // Buscar por pessoa (física ou jurídica)
    List<Endereco> findByPessoaId(Long pessoaId);

    // Buscar por empresa
    List<Endereco> findByEmpresaId(Long empresaId);

    // BUSCA AVANÇADA - combina todos os campos
    @Query("SELECT e FROM Endereco e WHERE " +
            "(:id IS NULL OR e.id = :id) AND " +
            "(:logradouro IS NULL OR LOWER(REPLACE(e.logradouro, ' ', '')) LIKE LOWER(REPLACE(CONCAT('%', :logradouro, '%'), ' ', ''))) AND " +
            "(:numero IS NULL OR e.numero LIKE CONCAT('%', :numero, '%')) AND " +
            "(:complemento IS NULL OR LOWER(REPLACE(e.complemento, ' ', '')) LIKE LOWER(REPLACE(CONCAT('%', :complemento, '%'), ' ', ''))) AND " +
            "(:bairro IS NULL OR LOWER(REPLACE(e.bairro, ' ', '')) LIKE LOWER(REPLACE(CONCAT('%', :bairro, '%'), ' ', ''))) AND " +
            "(:cidade IS NULL OR LOWER(REPLACE(e.cidade, ' ', '')) LIKE LOWER(REPLACE(CONCAT('%', :cidade, '%'), ' ', ''))) AND " +
            "(:uf IS NULL OR e.uf = :uf) AND " +
            "(:cep IS NULL OR REPLACE(e.cep, '-', '') LIKE CONCAT('%', REPLACE(:cep, '-', ''), '%')) AND " +
            "(:pessoaId IS NULL OR e.pessoa.id = :pessoaId) AND " +
            "(:empresaId IS NULL OR e.empresa.id = :empresaId)")
    List<Endereco> buscaAvancada(
            @Param("id") Long id,
            @Param("logradouro") String logradouro,
            @Param("numero") String numero,
            @Param("complemento") String complemento,
            @Param("bairro") String bairro,
            @Param("cidade") String cidade,
            @Param("uf") String uf,
            @Param("cep") String cep,
            @Param("pessoaId") Long pessoaId,
            @Param("empresaId") Long empresaId);
}