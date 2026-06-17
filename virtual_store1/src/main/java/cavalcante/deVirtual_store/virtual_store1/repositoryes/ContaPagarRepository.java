package cavalcante.deVirtual_store.virtual_store1.repositoryes;

import cavalcante.deVirtual_store.virtual_store1.enums.StatusContaPagarEnum;
import cavalcante.deVirtual_store.virtual_store1.models.ContaPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {
    List<ContaPagar> findByDescricaoContainingIgnoreCase(String descricao);
    List<ContaPagar> findByStatus(StatusContaPagarEnum status);
}