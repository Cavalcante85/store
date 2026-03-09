package cavalcante.deVirtual_store.virtual_store1.controllers;

import cavalcante.deVirtual_store.virtual_store1.enums.TipoPessoaEnum;
import cavalcante.deVirtual_store.virtual_store1.models.CategoriaProduto;
import cavalcante.deVirtual_store.virtual_store1.models.Pessoa;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaJuridica;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.CategoriaProdutoRepository;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaJuridicaRepository;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaginaPessoaJuridica {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @GetMapping("/Empresas")
    public String cadastrarEmpresa(Model model) {
        model.addAttribute("empresa", new PessoaJuridica());
        return "Empresa/cadastrar";
    }

    @PostMapping("/empresa/salvar")
    public String salvarPessoaJuridica(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("email") String email,
            @RequestParam("nome") String nome,
            @RequestParam("telefone") String telefone,
            @RequestParam("categoria") String categoria,  // ← CORRIGIDO
            @RequestParam("cnpj") String cnpj,
            @RequestParam("inscricao_estadual") String inscricaoEstadual,  // ← CORRIGIDO
            @RequestParam("inscricao_municipal") String inscricaoMunicipal,
            @RequestParam("nome_fantasia") String nomeFantasia,
            @RequestParam("razao_social") String razaoSocial,
            @RequestParam("tipo_pessoa") String tipoPessoa,  // ← CORRIGIDO
            @RequestParam("tipo") TipoPessoaEnum tipo,
            RedirectAttributes redirectAttributes) {

        try {
            PessoaJuridica pessoaJuridica = new PessoaJuridica();

            if (id != null && id > 0) {
                pessoaJuridica.setId(id);
            }

            pessoaJuridica.setEmail(email);
            pessoaJuridica.setNome(nome);
            pessoaJuridica.setTelefone(telefone);
            pessoaJuridica.setCategoria(categoria);
            pessoaJuridica.setCnpj(cnpj);
            pessoaJuridica.setInscricaoMunicipal(inscricaoMunicipal);
            pessoaJuridica.setInscricaoEstadual(inscricaoEstadual);
            pessoaJuridica.setRazaoSocial(razaoSocial);
            pessoaJuridica.setTipoPessoa(tipoPessoa);
            pessoaJuridica.setTipo(tipo);

            // Salvar no banco
            pessoaJuridicaRepository.save(pessoaJuridica);

            redirectAttributes.addFlashAttribute("mensagem", "Empresa salva com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/Empresas";  // ← CORRIGIDO (com E maiúsculo)
    }
}