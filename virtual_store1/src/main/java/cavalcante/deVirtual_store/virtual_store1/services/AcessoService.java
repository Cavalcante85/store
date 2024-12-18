package cavalcante.deVirtual_store.virtual_store1.services;

import cavalcante.deVirtual_store.virtual_store1.models.Acesso;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcessoService {

   @Autowired
   private AcessoRepository acessoRepository;

    public AcessoService(AcessoRepository acessoRepository) {
        this.acessoRepository = acessoRepository;
    }


    public Acesso save(Acesso acesso) {

        return  acessoRepository.save(acesso);
    }





}
