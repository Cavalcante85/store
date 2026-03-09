package cavalcante.deVirtual_store.virtual_store1.models;


import cavalcante.deVirtual_store.virtual_store1.enums.TipoPessoaEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_pessoa_fisica")
//@PrimaryKeyJoinColumn(name = "id")
public class PessoaFisica extends Pessoa implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private String cpf;
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    private String tipo_pessoa;
    @Enumerated(EnumType.STRING)
    private TipoPessoaEnum tipo;





    public TipoPessoaEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoaEnum tipo) {
        this.tipo = tipo;
    }

    public String getTipo_pessoa() {
        return tipo_pessoa;
    }

    public void setTipo_pessoa(String tipo_pessoa) {
        this.tipo_pessoa = tipo_pessoa;
    }

    public String getCpf() {

        return cpf;
    }

    public void setCpf(String cpf) {

        this.cpf = cpf;
    }

    public Date getDataNascimento() {

        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {

        this.dataNascimento = dataNascimento;
    }
}

