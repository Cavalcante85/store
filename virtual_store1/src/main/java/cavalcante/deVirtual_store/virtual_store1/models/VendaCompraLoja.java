package cavalcante.deVirtual_store.virtual_store1.models;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tb_vendasLoja")
public class VendaCompraLoja implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "pessoa_id", nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name="pessoa_fk"))
    private Pessoa pessoa;

    @ManyToOne(targetEntity = Endereco.class)
    @JoinColumn(name = "endereco_e_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "endereco_fk"))
    private Endereco enderecoEntrega;

    @ManyToOne
    @JoinColumn(name = "endereco_cb_id", nullable = false, foreignKey = @ForeignKey(value=ConstraintMode.CONSTRAINT,name="endereco_cb_fk"))
    private Endereco enderecoCobranca;

    private BigDecimal valor;

    private BigDecimal desconto;

    private BigDecimal frete;

    @Temporal(TemporalType.DATE)
    private Date dataVenda;

    @Temporal(TemporalType.DATE)
    private Date dataEntrega;

    private int diasEntrega;

    @ManyToOne(targetEntity = FormaPagamento.class)
    @JoinColumn(name = "forma_pagamento_id", nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "forma_pagamento_fk"))
    private FormaPagamento formaPagamento;

    @OneToOne
    @JoinColumn(name = "nota_fiscal_id",foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "nota_fiscal_fk"))
    private NotaFiscalVenda notaFiscalVenda;

    @ManyToOne(targetEntity = CupomDesconto.class)
    @JoinColumn(name = "cumpom_id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "cumpom_fk"))
    private CupomDesconto cupomDesconto;

    @Nullable
    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
    private Pessoa empresa;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Endereco getEnderecoCobranca() {
        return enderecoCobranca;
    }

    public void setEnderecoCobranca(Endereco enderecoCobranca) {
        this.enderecoCobranca = enderecoCobranca;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public int getDiasEntrega() {
        return diasEntrega;
    }

    public void setDiasEntrega(int diasEntrega) {
        this.diasEntrega = diasEntrega;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public NotaFiscalVenda getNotaFiscalVenda() {
        return notaFiscalVenda;
    }

    public void setNotaFiscalVenda(NotaFiscalVenda notaFiscalVenda) {
        this.notaFiscalVenda = notaFiscalVenda;
    }

    public CupomDesconto getCupomDesconto() {
        return cupomDesconto;
    }

    public void setCupomDesconto(CupomDesconto cupomDesconto) {
        this.cupomDesconto = cupomDesconto;
    }

    public Pessoa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Pessoa empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "VendaCompraLoja{" +
                "id=" + id +
                ", pessoa=" + pessoa +
                ", enderecoEntrega=" + enderecoEntrega +
                ", enderecoCobranca=" + enderecoCobranca +
                ", valor=" + valor +
                ", desconto=" + desconto +
                ", frete=" + frete +
                ", dataVenda=" + dataVenda +
                ", dataEntrega=" + dataEntrega +
                ", diasEntrega=" + diasEntrega +
                ", formaPagamento=" + formaPagamento +
                ", notaFiscalVenda=" + notaFiscalVenda +
                ", cupomDesconto=" + cupomDesconto +
                ", empresa=" + empresa +
                '}';
    }
}
