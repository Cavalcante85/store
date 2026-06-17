package cavalcante.deVirtual_store.virtual_store1.controllers;

import cavalcante.deVirtual_store.virtual_store1.models.Endereco;
import cavalcante.deVirtual_store.virtual_store1.models.Pessoa;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.EnderecoRepository;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaRepository;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaFisicaRepository;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaJuridicaRepository;
import cavalcante.deVirtual_store.virtual_store1.enums.TipoEnderecoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/endereco")
public class PaginaEnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    // ========== LISTAR TODOS OS ENDEREÇOS (RAIZ) ==========
    @GetMapping
    public String listarTodos(Model model) {
        List<Endereco> enderecos = enderecoRepository.findAll();

        // Filtrar endereços que têm referências válidas
        List<Endereco> enderecosValidos = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            boolean valido = true;

            // Verificar se a pessoa associada existe (se houver)
            if (endereco.getPessoa() != null) {
                try {
                    Long pessoaId = endereco.getPessoa().getId();
                    if (pessoaId != null && !pessoaRepository.existsById(pessoaId)) {
                        System.out.println("Endereço ID " + endereco.getId() + " tem pessoa inválida ID " + pessoaId);
                        valido = false;
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao verificar pessoa do endereço " + endereco.getId() + ": " + e.getMessage());
                    valido = false;
                }
            }

            // Verificar se a empresa associada existe (se houver)
            if (valido && endereco.getEmpresa() != null) {
                try {
                    Long empresaId = endereco.getEmpresa().getId();
                    if (empresaId != null && !pessoaRepository.existsById(empresaId)) {
                        System.out.println("Endereço ID " + endereco.getId() + " tem empresa inválida ID " + empresaId);
                        valido = false;
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao verificar empresa do endereço " + endereco.getId() + ": " + e.getMessage());
                    valido = false;
                }
            }

            if (valido) {
                enderecosValidos.add(endereco);
            }
        }

        model.addAttribute("resultados", enderecosValidos);
        model.addAttribute("endereco", new Endereco());
        model.addAttribute("pessoasFisicas", pessoaFisicaRepository.findAll());
        model.addAttribute("pessoasJuridicas", pessoaJuridicaRepository.findAll());
        model.addAttribute("tiposEndereco", TipoEnderecoEnum.values());
        return "Enderecos/criterio";
    }

    // ========== FORMULÁRIO DE CADASTRO ==========
    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("endereco", new Endereco());
        model.addAttribute("pessoasFisicas", pessoaFisicaRepository.findAll());
        model.addAttribute("pessoasJuridicas", pessoaJuridicaRepository.findAll());
        model.addAttribute("tiposEndereco", TipoEnderecoEnum.values());
        return "Enderecos/cadastrar";
    }

    // ========== SALVAR ENDEREÇO ==========
    @PostMapping("/salvar")
    public String salvar(
            @RequestParam(required = false) Long id,
            @RequestParam String logradouro,
            @RequestParam String numero,
            @RequestParam(required = false) String complemento,
            @RequestParam String bairro,
            @RequestParam String cidade,
            @RequestParam String uf,
            @RequestParam String cep,
            @RequestParam(required = false) Long pessoaId,
            @RequestParam(required = false) Long empresaId,
            @RequestParam String tipo,
            RedirectAttributes redirectAttributes) {

        try {
            Endereco endereco;

            if (id != null && id > 0) {
                endereco = enderecoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
            } else {
                endereco = new Endereco();
            }

            endereco.setLogradouro(logradouro);
            endereco.setNumero(numero);
            endereco.setComplemento(complemento);
            endereco.setBairro(bairro);
            endereco.setCidade(cidade);
            endereco.setUf(uf);
            endereco.setCep(cep);

            // Converter o tipo do enum corretamente
            try {
                endereco.setTipo(TipoEnderecoEnum.valueOf(tipo));
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("mensagem", "Tipo de endereço inválido: " + tipo);
                redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
                return "redirect:/endereco/cadastrar";
            }

            // ========== CORREÇÃO: Vincular à pessoa física OU empresa (apenas um) ==========
            // Limpar as associações primeiro
            endereco.setPessoa(null);
            endereco.setEmpresa(null);

            if (pessoaId != null && pessoaId > 0) {
                // Se selecionou uma pessoa física
                Optional<Pessoa> pessoaOpt = pessoaRepository.findById(pessoaId);
                if (pessoaOpt.isPresent()) {
                    endereco.setPessoa(pessoaOpt.get());
                    // Garante que empresa fique nula
                    endereco.setEmpresa(null);
                } else {
                    redirectAttributes.addFlashAttribute("mensagem", "Pessoa com ID " + pessoaId + " não encontrada!");
                    redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
                    return "redirect:/endereco/cadastrar";
                }
            } else if (empresaId != null && empresaId > 0) {
                // Se selecionou uma empresa
                Optional<Pessoa> empresaOpt = pessoaRepository.findById(empresaId);
                if (empresaOpt.isPresent()) {
                    endereco.setEmpresa(empresaOpt.get());
                    // Garante que pessoa fique nula
                    endereco.setPessoa(null);
                } else {
                    redirectAttributes.addFlashAttribute("mensagem", "Empresa com ID " + empresaId + " não encontrada!");
                    redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
                    return "redirect:/endereco/cadastrar";
                }
            }
            // Se ambos forem null, o endereço fica sem associação (válido também)

            enderecoRepository.save(endereco);

            redirectAttributes.addFlashAttribute("mensagem",
                    (id != null && id > 0) ? "Endereço atualizado com sucesso!" : "Endereço cadastrado com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/endereco";
    }

    // ========== EDITAR ENDEREÇO ==========
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        try {
            Endereco endereco = enderecoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

            model.addAttribute("endereco", endereco);
            model.addAttribute("pessoasFisicas", pessoaFisicaRepository.findAll());
            model.addAttribute("pessoasJuridicas", pessoaJuridicaRepository.findAll());
            model.addAttribute("tiposEndereco", TipoEnderecoEnum.values());
            return "Enderecos/cadastrar";

        } catch (Exception e) {
            return "redirect:/endereco";
        }
    }

    // ========== EXCLUIR ENDEREÇO ==========
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Endereco endereco = enderecoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

            enderecoRepository.delete(endereco);

            redirectAttributes.addFlashAttribute("mensagem", "Endereço excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/endereco";
    }

    // ========== VISUALIZAR ENDEREÇO ==========
    @GetMapping("/entidade/{id}")
    public String visualizar(@PathVariable Long id, Model model) {
        try {
            Endereco endereco = enderecoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

            model.addAttribute("endereco", endereco);
            return "Enderecos/entidade";

        } catch (Exception e) {
            return "redirect:/endereco";
        }
    }

    // ========== PÁGINA DE BUSCA (CRITÉRIO) ==========
    @GetMapping("/criterio")
    public String buscarForm(Model model) {
        model.addAttribute("endereco", new Endereco());
        model.addAttribute("pessoasFisicas", pessoaFisicaRepository.findAll());
        model.addAttribute("pessoasJuridicas", pessoaJuridicaRepository.findAll());
        model.addAttribute("tiposEndereco", TipoEnderecoEnum.values());
        return "Enderecos/criterio";
    }

    // ========== PROCESSAR BUSCA ==========
    @PostMapping("/pesquisar")
    public String pesquisar(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String logradouro,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) String complemento,
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) String cep,
            @RequestParam(required = false) Long pessoaId,
            @RequestParam(required = false) Long empresaId,
            Model model) {

        List<Endereco> resultados = new ArrayList<>();

        try {
            // VERIFICAÇÃO ESPECÍFICA PARA ID
            if (id != null && id > 0) {
                Optional<Endereco> enderecoOpt = enderecoRepository.findById(id);
                if (enderecoOpt.isPresent()) {
                    resultados.add(enderecoOpt.get());
                }
            }
            // SE NÃO TEM ID, FAZ A BUSCA AVANÇADA
            else {
                resultados = enderecoRepository.buscaAvancada(
                        id, logradouro, numero, complemento, bairro, cidade, uf, cep, pessoaId, empresaId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultados = enderecoRepository.findAll();
        }

        model.addAttribute("resultados", resultados);
        model.addAttribute("endereco", new Endereco());
        model.addAttribute("pessoasFisicas", pessoaFisicaRepository.findAll());
        model.addAttribute("pessoasJuridicas", pessoaJuridicaRepository.findAll());
        model.addAttribute("tiposEndereco", TipoEnderecoEnum.values());

        return "Enderecos/criterio";
    }
}