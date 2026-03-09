package cavalcante.deVirtual_store.virtual_store1.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class ReceitaFDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // Campos que SÃO listas (mantém como List)
    @JsonProperty("atividade_principal")
    private List<AtividadeDTO> atividade_principal;

    @JsonProperty("atividades_secundarias")
    private List<AtividadeDTO> atividades_secundarias;

    @JsonProperty("qsa")
    private List<QsaDTO> qsa;

    @JsonProperty("billing")
    private BillingDTO billing;  // ← GERALMENTE É OBJETO, NÃO LISTA!

    // Campos que SÃO objetos (NÃO são listas)
    @JsonProperty("simples")
    private SimplesDTO simples;  // ← MUDAR DE List<SimplesDTO> para SimplesDTO

    @JsonProperty("simei")
    private SimeiDTO simei;      // ← MUDAR DE List<SimeiDTO> para SimeiDTO

    // Campos simples (strings)
    private String abertura;
    private String situacao;
    private String tipo;
    private String nome;
    private String fantasia;
    private String porte;
    @JsonProperty("natureza_juridica")
    private String natureza_juridica;
    private String logradouro;
    private String numero;
    private String complemento;
    private String municipio;
    private String bairro;
    private String uf;
    private String cep;
    private String email;
    private String telefone;
    @JsonProperty("data_situacao")
    private String data_situacao;
    private String cnpj;
    @JsonProperty("ultima_atualizacao")
    private String ultima_atualizacao;
    private String status;
    private String efr;
    @JsonProperty("motivo_situacao")
    private String motivo_situacao;
    @JsonProperty("situacao_especial")
    private String situacao_especial;
    @JsonProperty("data_situacao_especial")
    private String data_situacao_especial;
    @JsonProperty("capital_social")
    private String capital_social;

    @JsonIgnore
    private ExtraDTO extra;

    // Getters e Setters - ajustar conforme as mudanças acima
    public List<AtividadeDTO> getAtividade_principal() {
        return atividade_principal;
    }

    public void setAtividade_principal(List<AtividadeDTO> atividade_principal) {
        this.atividade_principal = atividade_principal;
    }

    public List<AtividadeDTO> getAtividades_secundarias() {
        return atividades_secundarias;
    }

    public void setAtividades_secundarias(List<AtividadeDTO> atividades_secundarias) {
        this.atividades_secundarias = atividades_secundarias;
    }

    public List<QsaDTO> getQsa() {
        return qsa;
    }

    public void setQsa(List<QsaDTO> qsa) {
        this.qsa = qsa;
    }

    // ALTERADO: Agora é objeto, não lista
    public SimplesDTO getSimples() {
        return simples;
    }

    public void setSimples(SimplesDTO simples) {
        this.simples = simples;
    }

    // ALTERADO: Agora é objeto, não lista
    public SimeiDTO getSimei() {
        return simei;
    }

    public void setSimei(SimeiDTO simei) {
        this.simei = simei;
    }

    // ALTERADO: Billing geralmente é objeto
    public BillingDTO getBilling() {
        return billing;
    }

    public void setBilling(BillingDTO billing) {
        this.billing = billing;
    }

    // ... resto dos getters e setters (mantém iguais)
}