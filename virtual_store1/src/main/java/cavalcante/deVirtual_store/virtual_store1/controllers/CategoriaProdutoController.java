package cavalcante.deVirtual_store.virtual_store1.controllers;

import cavalcante.deVirtual_store.virtual_store1.dtos.CategoariaProdutoDTO;
import cavalcante.deVirtual_store.virtual_store1.models.CategoriaProduto;
import cavalcante.deVirtual_store.virtual_store1.models.Pessoa;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.CategoriaProdutoRepository;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaRepository; // Você precisa deste repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Autowired
    private PessoaRepository pessoaRepository; // Injete o repository de Pessoa

    @PostMapping(value = "salvarCategoria")
    public ResponseEntity<?> salvarCategoria(@RequestBody CategoariaProdutoDTO categoariaProdutoDTO) {

        try {
            // Buscar a empresa pelo ID
            Pessoa empresa = pessoaRepository.findById(categoariaProdutoDTO.getEmpresa_id())
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + categoariaProdutoDTO.getEmpresa_id()));

            // Criar a categoria
            CategoriaProduto categoriaProduto = new CategoriaProduto();
            categoriaProduto.setDescricao(categoariaProdutoDTO.getDescricao());
            categoriaProduto.setEmpresa(empresa);

            // Salvar
            CategoriaProduto categoriaProdutoSalvo = categoriaProdutoRepository.save(categoriaProduto);
            return new ResponseEntity<CategoriaProduto>(categoriaProdutoSalvo, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }


}