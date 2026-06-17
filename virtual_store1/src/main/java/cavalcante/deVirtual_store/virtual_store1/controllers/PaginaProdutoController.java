package cavalcante.deVirtual_store.virtual_store1.controllers;


import cavalcante.deVirtual_store.virtual_store1.repositoryes.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PaginaProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;






}
