package cavalcante.deVirtual_store.virtual_store1.controllers;

import cavalcante.deVirtual_store.virtual_store1.models.Acesso;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.AcessoRepository;
import cavalcante.deVirtual_store.virtual_store1.services.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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


    @ResponseBody
    @DeleteMapping(value ="/deleteAcessoPorId/{id}")
    public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id) {

        acessoRepository.deleteById(id);

        return new ResponseEntity("DELETADO",HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping(value ="/retornoAcessoPorId/{id}")
    public ResponseEntity<Acesso> retornoAcessoPorId(@PathVariable("id") Long id) {
        Acesso acesso = acessoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Acesso não encontrado"));

        return new ResponseEntity<>(acesso, HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping(value ="/retornoAcessoDesc/{descricao}")
    public ResponseEntity<List<Acesso>> retornoAcessoDesc(@PathVariable("descricao") String descricao) {

        List<Acesso> acesso = acessoRepository.buscarAcessoDesc(descricao);

        return new ResponseEntity<List<Acesso>>(acesso,HttpStatus.OK);
    }






}
