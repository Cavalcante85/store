package cavalcante.deVirtual_store.virtual_store1.services;

import cavalcante.deVirtual_store.virtual_store1.dtos.CepDTO;
import cavalcante.deVirtual_store.virtual_store1.dtos.ReceitaFDTO;
import cavalcante.deVirtual_store.virtual_store1.models.Endereco;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaFisica;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaJuridica;
import cavalcante.deVirtual_store.virtual_store1.models.Usuario;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

    public PessoaService(PessoaRepository pessoaRepository, UsuarioRepository usuarioRepository, EnderecoRepository enderecoRepository, JdbcTemplate jdbcTemplate, ServiceSendEmail serviceSendEmail,PessoaFisicaRepository pessoaFisicaRepository, PessoaJuridicaRepository pessoaJuridicaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.serviceSendEmail = serviceSendEmail;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }

    public PessoaJuridica salvarpj(PessoaJuridica pj) {
        pj = pessoaRepository.save(pj);

        Usuario usuariopj = usuarioRepository.findUserByPessoa(pj.getId(), pj.getEmail());

        if (usuariopj == null) {
            String constraint = usuarioRepository.consultaconstraintAcesso();
            if (constraint != null) {
                jdbcTemplate.execute("begin; alter table tb_usuarios_acesso drop constraint " + constraint + "; commit;");
            }

            String senha = "123456";
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            usuariopj = new Usuario();
            usuariopj.setDataSenha(Calendar.getInstance().getTime());
            usuariopj.setEmpresa(pj);
            usuariopj.setPessoa(pj);
            usuariopj.setLogin(pj.getEmail());
            usuariopj.setSenha(senhaCript);
            usuariopj = usuarioRepository.save(usuariopj);

            usuarioRepository.insereAcessoUserPj(usuariopj.getId());

            StringBuilder messageHtml = new StringBuilder();
            messageHtml.append("<b>Segue abaixo seus Acessos</b>");
            messageHtml.append("<br>Login : " + pj.getEmail() + " </br>");
            messageHtml.append("<br> Senha :" + senha + "</br></br>");
            messageHtml.append("<br>Obrigado</br>");

            try {
                serviceSendEmail.enviarEmaihtml("Acesso para sistema", messageHtml.toString(), pj.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Processa os endereços associados
            if (pj.getEnderecos() != null && !pj.getEnderecos().isEmpty()) {
                for (Endereco endereco : pj.getEnderecos()) {
                    endereco.setPessoa(pj); // Associa o endereço à pessoa
                    endereco.setEmpresa(pj); // Se necessário, ajuste conforme sua regra de negócio
                    // Se o endereço já tem ID, significa que é uma atualização
                    // Se não tem ID, é um novo endereço
                }
            }
            pj = pessoaRepository.save(pj);


        }

        return pj;
    }

    public PessoaFisica salvarpf(PessoaFisica pf) {
        // Salva a pessoa física primeiro
        pf = pessoaRepository.save(pf);

        Usuario usuarioPf = usuarioRepository.findUserByPessoa(pf.getId(), pf.getEmail());

        if (usuarioPf == null) {
            String constraint = usuarioRepository.consultaconstraintAcesso();
            if (constraint != null) {
                jdbcTemplate.execute("begin; alter table tb_usuarios_acesso drop constraint " + constraint + "; commit;");
            }

            String senha = "123456";
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            usuarioPf = new Usuario();
            usuarioPf.setDataSenha(Calendar.getInstance().getTime());
            usuarioPf.setEmpresa(pf);  //Ajuste conforme sua regra de negócio
            usuarioPf.setPessoa(pf);
            usuarioPf.setLogin(pf.getEmail());
            usuarioPf.setSenha(senhaCript);
            usuarioPf = usuarioRepository.save(usuarioPf);

            usuarioRepository.insereAcessoUserPj(usuarioPf.getId());


            StringBuilder messageHtml = new StringBuilder();
            messageHtml.append("<b>Segue abaixo seus Acessos</b>");
            messageHtml.append("<br>Login : " + pf.getEmail() + " </br>");
            messageHtml.append("<br> Senha :" + senha + "</br></br>");
            messageHtml.append("<br>Obrigado</br>");

            try {
                serviceSendEmail.enviarEmaihtml("Acesso para sistema", messageHtml.toString(), pf.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }


            // Processa os endereços associados
            if (pf.getEnderecos() != null && !pf.getEnderecos().isEmpty()) {
                for (Endereco endereco : pf.getEnderecos()) {
                    endereco.setPessoa(pf); // Associa o endereço à pessoa
                    endereco.setEmpresa(pf); // Se necessário, ajuste conforme sua regra de negócio
                    // Se o endereço já tem ID, significa que é uma atualização
                    // Se não tem ID, é um novo endereço
                }
                // Os endereços serão salvos automaticamente devido ao cascade = CascadeType.ALL

                // Salva a pessoa novamente para persistir os endereços com cascade
                pf = pessoaRepository.save(pf);

            }

        }

        return pf;
    }

    public CepDTO consultaCep(String cep) {
        return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json/", CepDTO.class).getBody();
    }

    public ReceitaFDTO consultaCnpjReceita(String cnpj) {
        return new RestTemplate().getForEntity("https://www.receitaws.com.br/v1/cnpj/" + cnpj + " ", ReceitaFDTO.class).getBody();
    }



    public List<PessoaFisica> consultaNome(String nome) {
        return pessoaFisicaRepository.findByNome(nome.trim());
    }

    public List<PessoaFisica> consultaLikeNome(String nome) {
        return pessoaFisicaRepository.onome(nome);
    }


    public List<PessoaFisica> consultaCpf(String cpf) {
        return pessoaFisicaRepository.findByCpf(cpf.trim());

    }

    public List<PessoaJuridica> consultaCnpj(String cnpj) {
        return pessoaJuridicaRepository.findByCnpj(cnpj);

    }


}