package cavalcante.deVirtual_store.virtual_store1.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
@Table(name = "tb_acesso")
public class Acesso implements GrantedAuthority {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String descricao;


    @Override
    public String getAuthority() {
        return this.descricao;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Acesso acesso = (Acesso) o;
        return id == acesso.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
