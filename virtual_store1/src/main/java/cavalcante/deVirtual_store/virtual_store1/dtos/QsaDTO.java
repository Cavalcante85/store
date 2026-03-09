package cavalcante.deVirtual_store.virtual_store1.dtos;

import java.io.Serializable;

public class QsaDTO implements Serializable {

    private String nome;
    private String qual;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }
}
