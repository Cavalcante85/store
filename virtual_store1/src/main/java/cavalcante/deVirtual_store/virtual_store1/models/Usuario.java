package cavalcante.deVirtual_store.virtual_store1.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String login;
    private String senha;
    @Temporal(TemporalType.DATE)
    private Date dataSenha;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable (name = "usuarios_acesso",uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id","acesso_id"},name = "unique_acesso_user"),
       joinColumns        = @JoinColumn(name = "usuario_id", referencedColumnName = "id", table = "usuario",unique = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "usuario_fk"))
      ,inverseJoinColumns = @JoinColumn(name = "acesso_id" , referencedColumnName = "id", table = "acesso", unique = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "acesso_fk"))
               )
    private List<Acesso> acessos;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.acessos;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
