package cavalcante.deVirtual_store.virtual_store1.dtos;

import java.io.Serializable;

public class CategoariaProdutoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String descricao;
    private long empresa_id;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(long empresa_id) {
        this.empresa_id = empresa_id;
    }
}
