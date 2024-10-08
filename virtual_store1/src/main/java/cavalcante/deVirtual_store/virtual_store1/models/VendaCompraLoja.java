package cavalcante.deVirtual_store.virtual_store1.models;

import jakarta.persistence.*;

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










}
