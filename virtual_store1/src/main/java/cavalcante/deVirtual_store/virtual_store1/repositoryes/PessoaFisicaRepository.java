package cavalcante.deVirtual_store.virtual_store1.repositoryes;

import cavalcante.deVirtual_store.virtual_store1.models.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {
    @Query(value = "SELECT CASE WHEN COUNT(pf) > 0 THEN true ELSE false END FROM PessoaFisica pf WHERE pf.cpf = :cpf")
    boolean existsByCpf(String cpf);

    @Query("SELECT n FROM PessoaFisica n WHERE n.nome LIKE %:nome%")
    List<PessoaFisica> onome(String nome);

    // Buscar por CPF (contendo parte do número)
    List<PessoaFisica> findByCpfContaining(String cpf);


    List<PessoaFisica> findByCpf(String cpf);

    List<PessoaFisica> findByNome(String nome);

    List<PessoaFisica> findByEmail(String email);

    // Buscar por nome (ignorando maiúsculas/minúsculas)
    List<PessoaFisica> findByNomeContainingIgnoreCase(String nome);

    // Buscar por email
    List<PessoaFisica> findByEmailContainingIgnoreCase(String email);

    // Buscar por telefone
    List<PessoaFisica> findByTelefoneContaining(String telefone);

    // Buscar por data de nascimento
    List<PessoaFisica> findByDataNascimento(Date dataNascimento);

    // BUSCA AVANÇADA - combina todos os campos
    @Query("SELECT pf FROM PessoaFisica pf WHERE " +
            "(:id IS NULL OR pf.id = :id) AND " +
            "(:nome IS NULL OR LOWER(pf.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "(:email IS NULL OR LOWER(pf.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:telefone IS NULL OR pf.telefone LIKE CONCAT('%', :telefone, '%')) AND " +
            "(:cpf IS NULL OR pf.cpf LIKE CONCAT('%', :cpf, '%')) AND " +
            "(:dataNascimento IS NULL OR pf.dataNascimento = :dataNascimento)")
    List<PessoaFisica> buscaAvancada(
            @Param("id") Long id,
            @Param("nome") String nome,
            @Param("email") String email,
            @Param("telefone") String telefone,
            @Param("cpf") String cpf,
            @Param("dataNascimento") Date dataNascimento);
}