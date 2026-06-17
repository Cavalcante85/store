package cavalcante.deVirtual_store.virtual_store1.controllers;

import cavalcante.deVirtual_store.virtual_store1.enums.TipoPessoaEnum;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaJuridica;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empresa")
public class PaginaPessoaJuridicaController {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    // ========== LISTAR TODAS EMPRESAS (RAIZ) ==========
    @GetMapping
    public String listarTodasEmpresas(Model model) {
        List<PessoaJuridica> empresas = pessoaJuridicaRepository.findAll();
        model.addAttribute("resultados", empresas);
        model.addAttribute("empresa", new PessoaJuridica());
        return "Empresa/criterio";
    }

    // ========== FORMULÁRIO DE CADASTRO ==========
    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("empresa", new PessoaJuridica());
        return "Empresa/cadastrar";
    }

    // ========== SALVAR EMPRESA ==========
    @PostMapping("/salvar")
    public String salvar(
            @RequestParam(required = false) Long id,
            @RequestParam String nome,
            @RequestParam(required = false) String nomeFantasia,
            @RequestParam String cnpj,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam(required = false) String inscricaoEstadual,
            @RequestParam(required = false) String inscricaoMunicipal,
            @RequestParam(required = false) String categoria,
            RedirectAttributes redirectAttributes) {

        try {
            // Verificar se CNPJ já existe (apenas para novos cadastros)
            if (id == null || id == 0) {
                List<PessoaJuridica> existCnpj = pessoaJuridicaRepository.findByCnpjContaining(cnpj);
                if (!existCnpj.isEmpty()) {
                    redirectAttributes.addFlashAttribute("mensagem", "CNPJ já cadastrado!");
                    redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
                    return "redirect:/empresa/cadastrar";
                }
            } else {
                // Na edição, verificar se o CNPJ pertence a outra empresa
                Optional<PessoaJuridica> empresaExistente = pessoaJuridicaRepository.findById(id);
                if (empresaExistente.isPresent() && !empresaExistente.get().getCnpj().equals(cnpj)) {
                    List<PessoaJuridica> existCnpj = pessoaJuridicaRepository.findByCnpjContaining(cnpj);
                    if (!existCnpj.isEmpty()) {
                        redirectAttributes.addFlashAttribute("mensagem", "CNPJ já cadastrado por outra empresa!");
                        redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
                        return "redirect:/empresa/editar/" + id;
                    }
                }
            }

            PessoaJuridica empresa;

            if (id != null && id > 0) {
                empresa = pessoaJuridicaRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + id));
            } else {
                empresa = new PessoaJuridica();
                empresa.setTipoPessoa(TipoPessoaEnum.CLIENTE.getDescricao());
            }

            empresa.setNome(nome);
            empresa.setNomeFantasia(nomeFantasia);
            empresa.setCnpj(cnpj);
            empresa.setEmail(email);
            empresa.setTelefone(telefone);
            empresa.setInscricaoEstadual(inscricaoEstadual);
            empresa.setInscricaoMunicipal(inscricaoMunicipal);
            empresa.setCategoria(categoria);

            pessoaJuridicaRepository.save(empresa);

            redirectAttributes.addFlashAttribute("mensagem",
                    (id != null && id > 0) ? "Empresa atualizada com sucesso!" : "Empresa cadastrada com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/empresa/criterio";
    }

    // ========== EDITAR EMPRESA ==========
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        try {
            PessoaJuridica empresa = pessoaJuridicaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + id));

            model.addAttribute("empresa", empresa);
            return "Empresa/cadastrar";

        } catch (Exception e) {
            return "redirect:/empresa";
        }
    }

    // ========== EXCLUIR EMPRESA ==========
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            PessoaJuridica empresa = pessoaJuridicaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + id));

            pessoaJuridicaRepository.delete(empresa);

            redirectAttributes.addFlashAttribute("mensagem", "Empresa excluída com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/empresa";
    }

    // ========== VISUALIZAR EMPRESA ==========
    @GetMapping("/entidade/{id}")
    public String visualizar(@PathVariable Long id, Model model) {
        try {
            PessoaJuridica empresa = pessoaJuridicaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + id));

            model.addAttribute("empresa", empresa);
            return "Empresa/entidade";

        } catch (Exception e) {
            return "redirect:/empresa";
        }
    }

    // ========== PÁGINA DE BUSCA (CRITÉRIO) ==========
    @GetMapping("/criterio")
    public String buscarForm(Model model) {
        model.addAttribute("empresa", new PessoaJuridica());
        return "Empresa/criterio";
    }

    // ========== PROCESSAR BUSCA ==========
    @PostMapping("/pesquisar")
    public String pesquisar(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String nomeFantasia,
            @RequestParam(required = false) String cnpj,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) String inscricaoEstadual,
            @RequestParam(required = false) String inscricaoMunicipal,
            @RequestParam(required = false) String categoria,
            Model model) {

        List<PessoaJuridica> resultados = new ArrayList<>();

        try {
            // VERIFICAÇÃO ESPECÍFICA PARA ID
            if (id != null && id > 0) {
                Optional<PessoaJuridica> empresaOpt = pessoaJuridicaRepository.findById(id);
                if (empresaOpt.isPresent()) {
                    resultados.add(empresaOpt.get());
                }
            }
            // SE NÃO TEM ID, FAZ A BUSCA POR CRITÉRIOS
            else {
                if (cnpj != null && !cnpj.isEmpty()) {
                    resultados = pessoaJuridicaRepository.findByCnpjContaining(cnpj);
                } else if (nome != null && !nome.isEmpty()) {
                    resultados = pessoaJuridicaRepository.findByNomeContainingIgnoreCase(nome);
                } else if (nomeFantasia != null && !nomeFantasia.isEmpty()) {
                    resultados = pessoaJuridicaRepository.findByNomeFantasiaContainingIgnoreCase(nomeFantasia);
                } else if (email != null && !email.isEmpty()) {
                    resultados = pessoaJuridicaRepository.findByEmailContainingIgnoreCase(email);
                } else {
                    resultados = pessoaJuridicaRepository.findAll();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultados = pessoaJuridicaRepository.findAll();
        }

        model.addAttribute("resultados", resultados);
        model.addAttribute("empresa", new PessoaJuridica());

        return "Empresa/criterio";
    }
}