package cavalcante.deVirtual_store.virtual_store1.controllers;

import cavalcante.deVirtual_store.virtual_store1.models.CategoriaProduto;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaFisica;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaJuridica;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaFisicaRepository;
import cavalcante.deVirtual_store.virtual_store1.enums.TipoPessoaEnum;
import cavalcante.deVirtual_store.virtual_store1.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Controller
@RequestMapping("/pessoa-fisica")
public class PaginaPessoaFisicaController {

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaService pessoaService;

    // ========== LISTAR TODAS PESSOAS (RAIZ) ==========
    @GetMapping
    public String listarTodasPessoas(Model model){
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        model.addAttribute("resultados", pessoaFisicaList);
        model.addAttribute("pessoaFisica", new PessoaFisica());
        return "Pessoas/criterio";
    }

    // ========== FORMULÁRIO DE CADASTRO ==========
    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("pessoaFisica", new PessoaFisica());
        return "Pessoas/cadastrar";
    }

    // ========== SALVAR PESSOA FÍSICA ==========
    @PostMapping("/salvar")
    public String salvar(@RequestParam(required = false) Long id, @RequestParam String nome, @RequestParam String email, @RequestParam String telefone, @RequestParam String cpf, @RequestParam String dataNascimento, RedirectAttributes redirectAttributes) {

        try {
            // Verificar se email já existe (apenas para novos cadastros)
            if (id == null || id == 0) {
                List<PessoaFisica> existEmail = pessoaFisicaRepository.findByEmail(email);
                if (!existEmail.isEmpty()) {
                    redirectAttributes.addFlashAttribute("mensagem", "Email já cadastrado!");
                    redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
                    return "redirect:/pessoa-fisica/cadastrar";  // Volta para o formulário
                }
            } else {
                // Na edição, verificar se o email pertence a outra pessoa
                PessoaFisica pessoaExistente = pessoaFisicaRepository.findById(id).orElse(null);
                if (pessoaExistente != null && !pessoaExistente.getEmail().equals(email)) {
                    List<PessoaFisica> existEmail = pessoaFisicaRepository.findByEmail(email);
                    if (!existEmail.isEmpty()) {
                        redirectAttributes.addFlashAttribute("mensagem", "Email já cadastrado por outra pessoa!");
                        redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
                        return "redirect:/pessoa-fisica/editar/" + id;
                    }
                }
            }

            PessoaFisica pessoa;

            if (id != null && id > 0) {
                pessoa = pessoaFisicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
            } else {
                pessoa = new PessoaFisica();
                pessoa.setTipo(TipoPessoaEnum.EMPREGADO);
            }
            pessoa.setTipo_pessoa("FISICA");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataNasc = sdf.parse(dataNascimento);

            pessoa.setNome(nome);
            pessoa.setEmail(email);
            pessoa.setTelefone(telefone);
            pessoa.setCpf(cpf);
            pessoa.setDataNascimento(dataNasc);

            //pessoaFisicaRepository.save(pessoa);
            pessoa = pessoaService.salvarpf(pessoa);

            redirectAttributes.addFlashAttribute("mensagem",
                    (id != null && id > 0) ? "Pessoa atualizada com sucesso!" : "Pessoa cadastrada com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/pessoa-fisica/criterio";
    }

    // ========== EDITAR PESSOA FÍSICA ==========
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        try {
            PessoaFisica pessoa = pessoaFisicaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

            model.addAttribute("pessoaFisica", pessoa);
            return "Pessoas/cadastrar";

        } catch (Exception e) {
            return "redirect:/pessoa-fisica/criterio";
        }
    }

    // ========== EXCLUIR PESSOA FÍSICA ==========
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            PessoaFisica pessoa = pessoaFisicaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

            pessoaFisicaRepository.delete(pessoa);

            redirectAttributes.addFlashAttribute("mensagem", "Pessoa excluída com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/pessoa-fisica/criterio";
    }

    // ========== VISUALIZAR PESSOA FÍSICA ==========
    @GetMapping("/entidade/{id}")
    public String visualizar(@PathVariable Long id, Model model) {
        try {
            PessoaFisica pessoa = pessoaFisicaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

            model.addAttribute("pessoa", pessoa);
            return "Pessoas/entidade";

        } catch (Exception e) {
            return "redirect:/pessoa-fisica/criterio";
        }
    }

    // ========== PÁGINA DE BUSCA (CRITÉRIO) ==========
    @GetMapping("/criterio")
    public String buscarForm(Model model) {
        model.addAttribute("pessoaFisica", new PessoaFisica());
        return "Pessoas/criterio";
    }

    // ========== PROCESSAR BUSCA ==========
    @PostMapping("/pesquisar")
    public String pesquisar(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String dataNascimento,
            Model model) {

        List<PessoaFisica> resultados;

        try {
            Date dataNasc = null;
            if (dataNascimento != null && !dataNascimento.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dataNasc = sdf.parse(dataNascimento);
            }

            resultados = pessoaFisicaRepository.buscaAvancada(id, nome, email, telefone, cpf, dataNasc);

        } catch (Exception e) {
            resultados = pessoaFisicaRepository.findAll();
            e.printStackTrace();
        }

        model.addAttribute("resultados", resultados);
        model.addAttribute("pessoaFisica", new PessoaFisica());

        return "Pessoas/criterio";
    }
}