package cavalcante.deVirtual_store.virtual_store1.controllers;

import cavalcante.deVirtual_store.virtual_store1.models.Acesso;
import cavalcante.deVirtual_store.virtual_store1.services.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @PostMapping("/salvarAcesso")
    public Acesso salvarAcesso(Acesso acesso) {
        return acessoService.save(acesso);
    }






}
