package cavalcante.deVirtual_store.virtual_store1.repositoryes;

import cavalcante.deVirtual_store.virtual_store1.models.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    // Verificar se CNPJ já existe (retorna true/false)
    boolean existsByCnpj(String cnpj);

    // Buscar por CNPJ (contendo parte do número)
    List<PessoaJuridica> findByCnpjContaining(String cnpj);

    // Buscar por CNPJ exato (retorna Optional)
    Optional<PessoaJuridica> findByCnpj(String cnpj);

    // Buscar por nome/razão social (ignorando maiúsculas/minúsculas)
    List<PessoaJuridica> findByNomeContainingIgnoreCase(String nome);

    // Buscar por nome fantasia (ignorando maiúsculas/minúsculas)
    List<PessoaJuridica> findByNomeFantasiaContainingIgnoreCase(String nomeFantasia);

    // Buscar por email (ignorando maiúsculas/minúsculas)
    List<PessoaJuridica> findByEmailContainingIgnoreCase(String email);

    // Verificar se email já existe
    boolean existsByEmail(String email);

    // Buscar por telefone
    List<PessoaJuridica> findByTelefoneContaining(String telefone);

    // Buscar por categoria
    List<PessoaJuridica> findByCategoriaContainingIgnoreCase(String categoria);

    // Buscar por inscrição estadual
    List<PessoaJuridica> findByInscricaoEstadualContaining(String inscricaoEstadual);

    // Buscar por inscrição municipal
    List<PessoaJuridica> findByInscricaoMunicipalContaining(String inscricaoMunicipal);
}