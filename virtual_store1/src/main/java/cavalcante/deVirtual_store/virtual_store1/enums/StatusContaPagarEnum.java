package cavalcante.deVirtual_store.virtual_store1.enums;

public enum StatusContaPagarEnum {


    COBRANCA("Pagar"),
    VENCIDA("Vencida"),
    ABERTA("Aberta"),
    QUITADA("Quitada");

    private String descricao;


    private StatusContaPagarEnum(String descricao) {
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
