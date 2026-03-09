package cavalcante.deVirtual_store.virtual_store1.repositoryes;


import cavalcante.deVirtual_store.virtual_store1.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
