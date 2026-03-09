package cavalcante.deVirtual_store.virtual_store1.controllers;

import cavalcante.deVirtual_store.virtual_store1.dtos.CepDTO;
import cavalcante.deVirtual_store.virtual_store1.dtos.ReceitaFDTO;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaFisica;
import cavalcante.deVirtual_store.virtual_store1.models.PessoaJuridica;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaFisicaRepository;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.PessoaJuridicaRepository;
import cavalcante.deVirtual_store.virtual_store1.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;


    @ResponseBody
    @GetMapping(value = "/consultaNome/{nome}")
    public ResponseEntity<List<PessoaFisica>> consultaNome(@PathVariable String nome) {

        List<PessoaFisica> existLista = pessoaService.consultaNome(nome);

        if (existLista.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nenhum Nome Encontrado!");
        }

        return ResponseEntity.ok(pessoaService.consultaNome(nome));
    }


    @ResponseBody
    @GetMapping(value = "/consultaLikeNome/{nome}")
    public ResponseEntity<List<PessoaFisica>> consultaLikeNome(@PathVariable String nome) {

        List<PessoaFisica> existLista = pessoaService.consultaLikeNome(nome);

        if (existLista.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nenhum Nome Encontrado!");
        }

        return ResponseEntity.ok(pessoaService.consultaLikeNome(nome));
    }



    @ResponseBody
    @GetMapping(value = "/consultaCpf/{cpf}")
    public ResponseEntity<List<PessoaFisica>> consultaCpf(@PathVariable String cpf) {

     List<PessoaFisica> existLista = pessoaService.consultaCpf(cpf);

     if (existLista.isEmpty()){
         throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nenhum CPF Encontrado!");
     }

        return ResponseEntity.ok(pessoaService.consultaCpf(cpf));
    }

    @ResponseBody
    @GetMapping(value = "/consultaCnpj/{cnpj}")
    public ResponseEntity<List<PessoaJuridica>> consultaCnpj(@PathVariable String cnpj) {

        List<PessoaJuridica> existLista = pessoaService.consultaCnpj(cnpj);

        if (existLista.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nenhum CNPJ Encontrado!");
        }

        return ResponseEntity.ok(pessoaService.consultaCnpj(cnpj));
    }



    @ResponseBody
    @GetMapping(value = "/consultacep/{cep}")
    public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep){
        CepDTO cepDTO = pessoaService.consultaCep(cep);
        return  new ResponseEntity<>(cepDTO,HttpStatus.OK);

    }


    @ResponseBody
    @GetMapping(value = "/consultaCnpjReceita/{cnpj}")
    public ResponseEntity<ReceitaFDTO> consultaCnpjReceita(@PathVariable("cnpj") String cnpj){
        ReceitaFDTO receitaFDTO = pessoaService.consultaCnpjReceita(cnpj);
        return  new ResponseEntity<>(receitaFDTO,HttpStatus.OK);

    }





    @PostMapping(value = "salvarPessoaPj")
    public ResponseEntity<PessoaJuridica> salvarPessoaPj(@RequestBody @Valid PessoaJuridica pessoaJuridica) {

        System.out.println("CNPJ recebido: " + pessoaJuridica.getCnpj());

        if (pessoaJuridica.getTipoPessoa() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tipo precisa ser cadastrado!");
        }

        // Verificação robusta do CNPJ
        boolean cnpjExistente = pessoaJuridicaRepository.existsByCnpj(pessoaJuridica.getCnpj());
        System.out.println("CNPJ existe no banco? " + cnpjExistente);

        if (cnpjExistente) {
            System.out.println("Tentativa de cadastrar CNPJ duplicado: " + pessoaJuridica.getCnpj());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CNPJ já cadastrado!");
        }


        for(int i = 0; i < pessoaJuridica.getEnderecos().size(); i++){
          CepDTO cepDTO = pessoaService.consultaCep(pessoaJuridica.getEnderecos().get(i).getCep());

          // Atualiza endereço com dados da API
          pessoaJuridica.getEnderecos().get(i).setBairro(cepDTO.getBairro());
          pessoaJuridica.getEnderecos().get(i).setLogradouro(cepDTO.getLogradouro());
          pessoaJuridica.getEnderecos().get(i).setCidade(cepDTO.getLocalidade());

        }
        PessoaJuridica pessoaSalvo = pessoaService.salvarpj(pessoaJuridica);
        return new ResponseEntity<>(pessoaSalvo, HttpStatus.OK);
    }

    @PostMapping(value = "salvarPessoaPf")
    public ResponseEntity<PessoaFisica> salvarPessoaPf(@RequestBody @Valid PessoaFisica pessoaFisica) {

        System.out.println("CPF recebido: " + pessoaFisica.getCpf());

        // Verificação de CPF duplicado
        boolean cpfExistente = pessoaFisicaRepository.existsByCpf(pessoaFisica.getCpf());
        System.out.println("CPF existe no banco? " + cpfExistente);

        if (cpfExistente) {
            System.out.println("Tentativa de cadastrar CPF duplicado: " + pessoaFisica.getCpf());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado!");
        }

       List<PessoaFisica> emailExist = pessoaFisicaRepository.findByEmail(pessoaFisica.getEmail());

        if(!emailExist.isEmpty()){
          throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email já cadastrado!") ;
        }

        PessoaFisica pessoaSalvo = pessoaService.salvarpf(pessoaFisica);
        return new ResponseEntity<>(pessoaSalvo, HttpStatus.OK);
    }


}