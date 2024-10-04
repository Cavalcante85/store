package cavalcante.deVirtual_store.virtual_store1.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_produto")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String tipoUnidade;

    private String nome;

    @Column(columnDefinition = "text",length = 1000)
    private String descricao;
    private boolean ativo = true;
    private double peso;
    private double altura;
    private double largura;
    private double profundidade;
    private double valor;
    private int quantidade;
    private int quantidadeCritica;
    private String link_produto;
    private boolean statusEstoqueCritico = false;
    private int quantidadeCliques;



   public Produto() {

   }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipoUnidade() {
        return tipoUnidade;
    }

    public void setTipoUnidade(String tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public double getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(double profundidade) {
        this.profundidade = profundidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidadeCritica() {
        return quantidadeCritica;
    }

    public void setQuantidadeCritica(int quantidadeCritica) {
        this.quantidadeCritica = quantidadeCritica;
    }

    public String getLink_produto() {
        return link_produto;
    }

    public void setLink_produto(String link_produto) {
        this.link_produto = link_produto;
    }

    public boolean isStatusEstoqueCritico() {
        return statusEstoqueCritico;
    }

    public void setStatusEstoqueCritico(boolean statusEstoqueCritico) {
        this.statusEstoqueCritico = statusEstoqueCritico;
    }

    public int getQuantidadeCliques() {
        return quantidadeCliques;
    }

    public void setQuantidadeCliques(int quantidadeCliques) {
        this.quantidadeCliques = quantidadeCliques;
    }


    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", tipoUnidade='" + tipoUnidade + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ativo=" + ativo +
                ", peso=" + peso +
                ", altura=" + altura +
                ", largura=" + largura +
                ", profundidade=" + profundidade +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", quantidadeCritica=" + quantidadeCritica +
                ", link_produto='" + link_produto + '\'' +
                ", statusEstoqueCritico=" + statusEstoqueCritico +
                ", quantidadeCliques=" + quantidadeCliques +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
