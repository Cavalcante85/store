package cavalcante.deVirtual_store.virtual_store1.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "tb_status_rastreio")
public class StatusRastreio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String cd;
    private String cidade;
    private String estado;
    private String status;

    @ManyToOne(targetEntity = VendaCompraLoja.class)
    @JoinColumn(name = "venda_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT,name = "venda_fk"))
    private VendaCompraLoja venda;


   public StatusRastreio() {

   }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public VendaCompraLoja getVenda() {
        return venda;
    }

    public void setVenda(VendaCompraLoja venda) {
        this.venda = venda;
    }

    @Override
    public String toString() {
        return "StatusRastreio{" +
                "id=" + id +
                ", cd='" + cd + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", status='" + status + '\'' +
                ", venda=" + venda +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusRastreio that = (StatusRastreio) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
