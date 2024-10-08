package cavalcante.deVirtual_store.virtual_store1.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_item_venda")
public class ItemVenda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private int quantidade;

    @ManyToOne(targetEntity = Produto.class)
    @JoinColumn(name = "produto_id", nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT,name = "produto_fk"))
    private Produto produto;

    @ManyToOne(targetEntity = VendaCompraLoja.class)
    @JoinColumn(name = "venda_id", nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name="venda_fk"))
    private VendaCompraLoja venda;


     public ItemVenda() {

     }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public VendaCompraLoja getVenda() {
        return venda;
    }

    public void setVenda(VendaCompraLoja venda) {
        this.venda = venda;
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", produto=" + produto +
                ", venda=" + venda +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemVenda itemVenda = (ItemVenda) o;
        return id == itemVenda.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
