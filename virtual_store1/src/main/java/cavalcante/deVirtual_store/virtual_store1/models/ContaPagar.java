package cavalcante.deVirtual_store.virtual_store1.models;

import cavalcante.deVirtual_store.virtual_store1.enums.StatusContaPagarEnum;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_conta_pagar")
public class ContaPagar implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String descricao;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Temporal(TemporalType.DATE)
    private Date dtVencimento;

    @Temporal(TemporalType.DATE)
    private Date dtPagamento;

    @Enumerated(EnumType.STRING)
    StatusContaPagarEnum status;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "pessoa_id", nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "pessoa_fk"))
    private Pessoa pessoa;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "pessoa_forn_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "pessoaf_fk"))
    private Pessoa pessoa_fornecedor;


    public ContaPagar() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Date getDtPagamento() {
        return dtPagamento;
    }

    public void setDtPagamento(Date dtPagamento) {
        this.dtPagamento = dtPagamento;
    }

    public StatusContaPagarEnum getStatus() {
        return status;
    }

    public void setStatus(StatusContaPagarEnum status) {
        this.status = status;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Pessoa getPessoa_fornecedor() {
        return pessoa_fornecedor;
    }

    public void setPessoa_fornecedor(Pessoa pessoa_fornecedor) {
        this.pessoa_fornecedor = pessoa_fornecedor;
    }


    @Override
    public String toString() {
        return "ContaPagar{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", valorTotal=" + valorTotal +
                ", dtVencimento=" + dtVencimento +
                ", dtPagamento=" + dtPagamento +
                ", status=" + status +
                ", pessoa=" + pessoa +
                ", pessoa_fornecedor=" + pessoa_fornecedor +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaPagar that = (ContaPagar) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
