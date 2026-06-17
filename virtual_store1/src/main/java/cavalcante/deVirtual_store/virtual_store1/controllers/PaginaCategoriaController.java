package cavalcante.deVirtual_store.virtual_store1.controllers;
import cavalcante.deVirtual_store.virtual_store1.models.CategoriaProduto;
import cavalcante.deVirtual_store.virtual_store1.models.Pessoa;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaJuridica;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.CategoriaProdutoRepository;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaJuridicaRepository;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PaginaCategoriaController {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    // ========== FORMULÁRIO DE CADASTRO ==========
    @GetMapping("/categoria/cadastrar")
    public String cadastrarCategoria(Model model) {
        model.addAttribute("empresas", pessoaJuridicaRepository.findAll());
        model.addAttribute("categoria", new CategoriaProduto());
        return "Categoria/cadastrar";
    }


    @GetMapping("/categorias")
    public String listarTodasCategorias(Model model){

        List<CategoriaProduto> categoriaProdutoList = categoriaProdutoRepository.findAll();
        model.addAttribute("resultados",categoriaProdutoList);
        model.addAttribute("categoria", new CategoriaProduto());
        model.addAttribute("empresas",pessoaJuridicaRepository.findAll());

         return "Categoria/criterio";
    }


    // ========== SALVAR CATEGORIA (NOVA OU EDIÇÃO) ==========
    @PostMapping("/categoria/salvar")
    public String salvarCategoria(
            @RequestParam(required = false) Long id,
            @RequestParam String descricao,
            @RequestParam Long empresaId,
            RedirectAttributes redirectAttributes) {

        try {
            // Buscar a empresa pelo ID
            Pessoa empresa = pessoaRepository.findById(empresaId)
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + empresaId));

            CategoriaProduto categoria;

            // Se tem ID, é edição - busca categoria existente
            if (id != null && id > 0) {
                categoria = categoriaProdutoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));
                categoria.setDescricao(descricao);
                categoria.setEmpresa(empresa);
            } else {
                // Novo cadastro
                categoria = new CategoriaProduto();
                categoria.setDescricao(descricao);
                categoria.setEmpresa(empresa);
            }

            // Salvar no banco
            categoriaProdutoRepository.save(categoria);

            redirectAttributes.addFlashAttribute("mensagem",
                    (id != null && id > 0) ? "Categoria atualizada com sucesso!" : "Categoria cadastrada com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/categoria/criterio";
    }

    // ========== EDITAR CATEGORIA ==========
    @GetMapping("/categoria/editar/{id}")
    public String editarCategoria(@PathVariable Long id, Model model) {
        try {
            CategoriaProduto categoria = categoriaProdutoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));

            model.addAttribute("empresas", pessoaJuridicaRepository.findAll());
            model.addAttribute("categoria", categoria);
            return "Categoria/cadastrar";

        } catch (Exception e) {
            return "redirect:/categoria/criterio";
        }
    }

    // ========== EXCLUIR CATEGORIA ==========
    @GetMapping("/categoria/excluir/{id}")
    public String excluirCategoria(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            CategoriaProduto categoria = categoriaProdutoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));

            categoriaProdutoRepository.delete(categoria);

            redirectAttributes.addFlashAttribute("mensagem", "Categoria excluída com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/categoria/criterio";
    }

    // ========== VISUALIZAR CATEGORIA ==========
    @GetMapping("/categoria/entidade/{id}")
    public String visualizarCategoria(@PathVariable Long id, Model model) {
        try {
            CategoriaProduto categoria = categoriaProdutoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));

            model.addAttribute("categoria", categoria);
            return "Categoria/entidade";

        } catch (Exception e) {
            return "redirect:/categoria/criterio";
        }
    }

    // ========== PÁGINA DE BUSCA (CRITÉRIO) ==========
    @GetMapping("/categoria/criterio")
    public String buscarCategoriaForm(Model model) {
        model.addAttribute("categoria", new CategoriaProduto());
        model.addAttribute("empresas", pessoaJuridicaRepository.findAll());
        return "Categoria/criterio";
    }

    // ========== PROCESSAR BUSCA ==========
    @PostMapping("/categoria/pesquisar")
    public String pesquisarCategoria(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) Long empresaId,
            Model model) {

        List<CategoriaProduto> resultados;

        try {
            // Usando a busca avançada do repository
            resultados = categoriaProdutoRepository.buscaAvancada(id, descricao, empresaId);

            // Se não encontrou nada com a busca avançada, tenta a busca simples
            if (resultados.isEmpty()) {
                if (id != null) {
                    resultados = categoriaProdutoRepository.findById(id)
                            .map(List::of)
                            .orElse(List.of());
                } else if (descricao != null && !descricao.isEmpty()) {
                    resultados = categoriaProdutoRepository.findByDescricaoContainingIgnoreCase(descricao);
                } else if (empresaId != null) {
                    resultados = categoriaProdutoRepository.findByEmpresaId(empresaId);
                } else {
                    resultados = categoriaProdutoRepository.findAll();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultados = categoriaProdutoRepository.findAll();
        }

        model.addAttribute("resultados", resultados);
        model.addAttribute("categoria", new CategoriaProduto());
        model.addAttribute("empresas", pessoaJuridicaRepository.findAll());

        return "Categoria/criterio";
    }
}