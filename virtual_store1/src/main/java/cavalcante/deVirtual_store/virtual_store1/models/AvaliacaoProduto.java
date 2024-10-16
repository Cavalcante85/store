package cavalcante.deVirtual_store.virtual_store1.models;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name = "tb_avaliacao_produto")
public class AvaliacaoProduto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String descricao;

    @Column(nullable = false)
    private Integer nota;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT,name = "pessoa_fk"))
    private Pessoa pessoa;


    @ManyToOne(targetEntity = Produto.class)
    @JoinColumn(name = "produto_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "produto_fk"))
    private Produto produto;



}
