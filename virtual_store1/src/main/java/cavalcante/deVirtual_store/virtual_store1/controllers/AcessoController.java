package cavalcante.deVirtual_store.virtual_store1.controllers;

import cavalcante.deVirtual_store.virtual_store1.models.Acesso;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.AcessoRepository;
import cavalcante.deVirtual_store.virtual_store1.services.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @ResponseBody
    @PostMapping(value ="/salvarAcesso")
    public ResponseEntity <Acesso> salvarAcesso(@RequestBody Acesso acesso) {

        Acesso acessoSalvo = acessoService.save(acesso);

        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
    }


    @ResponseBody
    @DeleteMapping(value ="/deleteAcesso")
    public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso) {

        acessoRepository.deleteById(acesso.getId());

        return new ResponseEntity("DELETADO",HttpStatus.OK);
    }






}
