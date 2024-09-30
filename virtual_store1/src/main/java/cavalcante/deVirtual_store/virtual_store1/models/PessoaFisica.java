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

