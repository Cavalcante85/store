package cavalcante.deVirtual_store.virtual_store1.models;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_pessoa_fisica")
//@PrimaryKeyJoinColumn(name = "id")
public class PessoaFisica extends Pessoa {
    private static final long serialVersionUID = 1L;


    private String cpf;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    private String tipo_pessoa;

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

