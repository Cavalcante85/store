package cavalcante.deVirtual_store.virtual_store1.repositoryes;

import cavalcante.deVirtual_store.virtual_store1.models.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {

    // Buscar por descrição (parte dela, ignorando maiúsculas/minúsculas)
    List<CategoriaProduto> findByDescricaoContainingIgnoreCase(String descricao);

    // Buscar por ID da empresa
    List<CategoriaProduto> findByEmpresaId(Long empresaId);

    // BUSCA AVANÇADA - combina todos os critérios
    @Query("SELECT c FROM CategoriaProduto c WHERE " +
            "(:id IS NULL OR c.id = :id) AND " +
            "(:descricao IS NULL OR LOWER(c.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))) AND " +
            "(:empresaId IS NULL OR c.empresa.id = :empresaId)")
    List<CategoriaProduto> buscaAvancada(
            @Param("id") Long id,
            @Param("descricao") String descricao,
            @Param("empresaId") Long empresaId);
}