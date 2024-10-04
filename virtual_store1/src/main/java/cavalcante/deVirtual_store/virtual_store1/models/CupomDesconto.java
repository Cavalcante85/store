package cavalcante.deVirtual_store.virtual_store1.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_cupom_desc")
public class CupomDesconto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String codigo;
    private BigDecimal valor_real_desc;
    private BigDecimal valor_perc_desc;

    @Temporal(TemporalType.DATE)
    private Date dt_desc;

    public CupomDesconto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getValor_real_desc() {
        return valor_real_desc;
    }

    public void setValor_real_desc(BigDecimal valor_real_desc) {
        this.valor_real_desc = valor_real_desc;
    }

    public BigDecimal getValor_perc_desc() {
        return valor_perc_desc;
    }

    public void setValor_perc_desc(BigDecimal valor_perc_desc) {
        this.valor_perc_desc = valor_perc_desc;
    }

    public Date getDt_desc() {
        return dt_desc;
    }

    public void setDt_desc(Date dt_desc) {
        this.dt_desc = dt_desc;
    }

    @Override
    public String toString() {
        return "CupomDesconto{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", valor_real_desc=" + valor_real_desc +
                ", valor_perc_desc=" + valor_perc_desc +
                ", dt_desc=" + dt_desc +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CupomDesconto that = (CupomDesconto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}


