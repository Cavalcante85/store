package cavalcante.deVirtual_store.virtual_store1.enums;

public enum TipoEnderecoEnum {


    COBRANCA("Cobrança"),
    ENTREGA("Entrega");

    private String descricao;

    private TipoEnderecoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
