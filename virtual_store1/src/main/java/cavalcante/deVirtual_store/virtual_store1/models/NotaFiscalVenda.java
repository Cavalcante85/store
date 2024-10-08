package cavalcante.deVirtual_store.virtual_store1.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_nota_fiscal")
public class NotaFiscalVenda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String numero;
    private String serie;
    private String tipo;
    @Column(columnDefinition = "text")
    private String xml;
    @Column(columnDefinition = "text")
    private String pdf;

    @OneToOne
    @JoinColumn(name = "venda_id", nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "venda_fk"))
    private VendaCompraLoja venda;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public VendaCompraLoja getVenda() {
        return venda;
    }

    public void setVenda(VendaCompraLoja venda) {
        this.venda = venda;
    }

    public NotaFiscalVenda() {

    }

    @Override
    public String toString() {
        return "NotaFiscalVenda{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", serie='" + serie + '\'' +
                ", tipo='" + tipo + '\'' +
                ", xml='" + xml + '\'' +
                ", pdf='" + pdf + '\'' +
                ", venda=" + venda +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotaFiscalVenda that = (NotaFiscalVenda) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
