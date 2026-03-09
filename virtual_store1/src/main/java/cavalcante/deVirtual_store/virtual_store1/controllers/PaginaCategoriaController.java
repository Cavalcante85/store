package cavalcante.deVirtual_store.virtual_store1.controllers;

import cavalcante.deVirtual_store.virtual_store1.models.CategoriaProduto;
import cavalcante.deVirtual_store.virtual_store1.models.Pessoa;
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
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @GetMapping({"/", "/index"})
    public String home() {
        return "index";
    }

    @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        List<CategoriaProduto> categorias = categoriaProdutoRepository.findAll();

        System.out.println("========== DEBUG DA LISTAGEM ==========");
        System.out.println("Número de categorias encontradas: " + categorias.size());
        for (CategoriaProduto cat : categorias) {
            System.out.println("ID: " + cat.getId() +
                    " | Descrição: " + cat.getDescricao() +
                    " | Empresa: " + (cat.getEmpresa() != null ? cat.getEmpresa().getNome() : "N/A"));
        }
        System.out.println("========================================");

        model.addAttribute("categorias", categorias);
        return "Categoria/listar";
    }

    @GetMapping("/categoria/cadastrar")
    public String cadastrarCategoria(Model model) {
        model.addAttribute("empresas", pessoaJuridicaRepository.findAll());
        model.addAttribute("categoria", new CategoriaProduto());
        return "Categoria/cadastrar";
    }

    @PostMapping("/categoria/salvar")
    public String salvarCategoria(
            @RequestParam(value = "id", required = false) Long id,  // Mudado para required=false
            @RequestParam("descricao") String descricao,
            @RequestParam("empresaId") Long empresaId,
            RedirectAttributes redirectAttributes) {

        try {
            Pessoa empresa = pessoaRepository.findById(empresaId)
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + empresaId));

            CategoriaProduto categoria;

            // Se tem ID, busca existente para editar
            if (id != null && id > 0) {
                categoria = categoriaProdutoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
                categoria.setDescricao(descricao);
                categoria.setEmpresa(empresa);
                System.out.println(" Categoria atualizada! ID: " + id);
            } else {
                // Novo registro
                categoria = new CategoriaProduto();
                categoria.setDescricao(descricao);
                categoria.setEmpresa(empresa);
            }

            categoriaProdutoRepository.save(categoria);

            System.out.println(" Categoria salva com sucesso! ID: " + categoria.getId() + " - " + descricao);

            redirectAttributes.addFlashAttribute("mensagem",
                    (id != null && id > 0) ? "Categoria atualizada com sucesso!" : "Categoria salva com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/categorias";
    }

    // ===== NOVO MÉTODO PARA EDITAR (exibir formulário preenchido) =====
    @GetMapping("/categoria/editar/{id}")
    public String editarCategoria(@PathVariable Long id, Model model) {
        try {
            CategoriaProduto categoria = categoriaProdutoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

            model.addAttribute("empresas", pessoaJuridicaRepository.findAll());
            model.addAttribute("categoria", categoria);
            return "Categoria/cadastrar"; // Reusa o mesmo formulário

        } catch (Exception e) {
            return "redirect:/categorias";
        }
    }

    // ===== MÉTODO DE EXCLUSÃO MOVIDO PARA CÁ =====
    @GetMapping("/categoria/excluir/{id}")
    public String excluirCategoria(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            CategoriaProduto categoria = categoriaProdutoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

            categoriaProdutoRepository.delete(categoria);

            redirectAttributes.addFlashAttribute("mensagem", "Categoria excluída com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

            System.out.println(" Categoria excluída! ID: " + id);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/categorias";
    }
}