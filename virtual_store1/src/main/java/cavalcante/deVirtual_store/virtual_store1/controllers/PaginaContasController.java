package cavalcante.deVirtual_store.virtual_store1.controllers;

import cavalcante.deVirtual_store.virtual_store1.enums.StatusContaPagarEnum;
import cavalcante.deVirtual_store.virtual_store1.models.ContaPagar;
import cavalcante.deVirtual_store.virtual_store1.models.Pessoa;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.ContaPagarRepository;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaRepository;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaFisicaRepository;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/contas")  // ← Mapeamento para /Contas
public class PaginaContasController {

    @Autowired
    private ContaPagarRepository contaPagarRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @GetMapping
    public String listarTodas(Model model) {
        List<ContaPagar> contas = contaPagarRepository.findAll();
        model.addAttribute("resultados", contas);
        model.addAttribute("contaPagar", new ContaPagar());
        model.addAttribute("statusList", StatusContaPagarEnum.values());
        return "contas/criterio";
    }

    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("contaPagar", new ContaPagar());
        model.addAttribute("statusList", StatusContaPagarEnum.values());
        model.addAttribute("pessoasFisicas", pessoaFisicaRepository.findAll());
        model.addAttribute("pessoasJuridicas", pessoaJuridicaRepository.findAll());
        model.addAttribute("empresas", pessoaJuridicaRepository.findAll());
        return "contas/cadastrar";
    }

    @PostMapping("/salvar")
    public String salvar(
            @RequestParam(required = false) Long id,
            @RequestParam String descricao,
            @RequestParam String valorTotal,
            @RequestParam String dtVencimento,
            @RequestParam(required = false) String dtPagamento,
            @RequestParam String status,
            @RequestParam(required = false) Long pessoaId,
            @RequestParam(required = false) Long pessoaFornecedorId,
            @RequestParam(required = false) Long empresaId,
            RedirectAttributes redirectAttributes) {

        System.out.println("=== DEBUG SALVAR CONTA ===");
        System.out.println("ID: " + id);
        System.out.println("Descrição: " + descricao);
        System.out.println("Valor Total: " + valorTotal);
        System.out.println("Data Vencimento: " + dtVencimento);
        System.out.println("Data Pagamento: " + dtPagamento);
        System.out.println("Status: " + status);
        System.out.println("Pessoa ID: " + pessoaId);
        System.out.println("Fornecedor ID: " + pessoaFornecedorId);
        System.out.println("Empresa ID: " + empresaId);

        try {
            ContaPagar conta;

            if (id != null && id > 0) {
                conta = contaPagarRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + id));
            } else {
                conta = new ContaPagar();
            }

            conta.setDescricao(descricao);
            conta.setValorTotal(new BigDecimal(valorTotal));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (dtVencimento != null && !dtVencimento.isEmpty()) {
                conta.setDtVencimento(sdf.parse(dtVencimento));
            }
            if (dtPagamento != null && !dtPagamento.isEmpty()) {
                conta.setDtPagamento(sdf.parse(dtPagamento));
            }

            try {
                conta.setStatus(StatusContaPagarEnum.valueOf(status));
            } catch (IllegalArgumentException e) {
                System.out.println("Status inválido: " + status);
                redirectAttributes.addFlashAttribute("mensagem", "Status inválido: " + status);
                redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
                return "redirect:/contas/cadastrar";
            }

            // Vincular pessoa (quem está pagando)
            if (pessoaId != null && pessoaId > 0) {
                Optional<Pessoa> pessoaOpt = pessoaRepository.findById(pessoaId);
                if (pessoaOpt.isPresent()) {
                    conta.setPessoa(pessoaOpt.get());
                    System.out.println("Pessoa vinculada: " + pessoaOpt.get().getNome());
                } else {
                    System.out.println("Pessoa não encontrada com ID: " + pessoaId);
                }
            }

            // Vincular fornecedor
            if (pessoaFornecedorId != null && pessoaFornecedorId > 0) {
                Optional<Pessoa> fornecedorOpt = pessoaRepository.findById(pessoaFornecedorId);
                if (fornecedorOpt.isPresent()) {
                    conta.setPessoa_fornecedor(fornecedorOpt.get());
                    System.out.println("Fornecedor vinculado: " + fornecedorOpt.get().getNome());
                } else {
                    System.out.println("Fornecedor não encontrado com ID: " + pessoaFornecedorId);
                }
            }

            // Vincular empresa
            if (empresaId != null && empresaId > 0) {
                Optional<Pessoa> empresaOpt = pessoaRepository.findById(empresaId);
                if (empresaOpt.isPresent()) {
                    conta.setEmpresa(empresaOpt.get());
                    System.out.println("Empresa vinculada: " + empresaOpt.get().getNome());
                } else {
                    System.out.println("Empresa não encontrada com ID: " + empresaId);
                }
            }

            contaPagarRepository.save(conta);
            System.out.println("Conta salva com sucesso! ID: " + conta.getId());

            redirectAttributes.addFlashAttribute("mensagem",
                    (id != null && id > 0) ? "Conta atualizada com sucesso!" : "Conta cadastrada com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            System.out.println("ERRO AO SALVAR: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
        }

        return "redirect:/contas/criterio";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        try {
            ContaPagar conta = contaPagarRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + id));

            model.addAttribute("contaPagar", conta);
            model.addAttribute("statusList", StatusContaPagarEnum.values());
            model.addAttribute("pessoasFisicas", pessoaFisicaRepository.findAll());
            model.addAttribute("pessoasJuridicas", pessoaJuridicaRepository.findAll());
            model.addAttribute("empresas", pessoaJuridicaRepository.findAll());
            return "contas/cadastrar";

        } catch (Exception e) {
            return "redirect:/contas";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            ContaPagar conta = contaPagarRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + id));

            contaPagarRepository.delete(conta);

            redirectAttributes.addFlashAttribute("mensagem", "Conta excluída com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/contas";
    }

    @GetMapping("/entidade/{id}")
    public String visualizar(@PathVariable Long id, Model model) {
        try {
            ContaPagar conta = contaPagarRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + id));

            model.addAttribute("contaPagar", conta);
            return "contas/entidade";

        } catch (Exception e) {
            return "redirect:/contas";
        }
    }

    @GetMapping("/criterio")
    public String buscarForm(Model model) {
        model.addAttribute("contaPagar", new ContaPagar());
        model.addAttribute("statusList", StatusContaPagarEnum.values());
        return "contas/criterio";
    }

    @PostMapping("/pesquisar")
    public String pesquisar(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String status,
            Model model) {

        List<ContaPagar> resultados = new ArrayList<>();

        try {
            if (id != null && id > 0) {
                Optional<ContaPagar> contaOpt = contaPagarRepository.findById(id);
                contaOpt.ifPresent(resultados::add);
            } else if (descricao != null && !descricao.isEmpty()) {
                resultados = contaPagarRepository.findByDescricaoContainingIgnoreCase(descricao);
            } else if (status != null && !status.isEmpty()) {
                try {
                    StatusContaPagarEnum statusEnum = StatusContaPagarEnum.valueOf(status);
                    resultados = contaPagarRepository.findByStatus(statusEnum);
                } catch (IllegalArgumentException e) {
                    resultados = contaPagarRepository.findAll();
                }
            } else {
                resultados = contaPagarRepository.findAll();
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultados = contaPagarRepository.findAll();
        }

        model.addAttribute("resultados", resultados);
        model.addAttribute("contaPagar", new ContaPagar());
        model.addAttribute("statusList", StatusContaPagarEnum.values());

        return "contas/criterio";
    }
}