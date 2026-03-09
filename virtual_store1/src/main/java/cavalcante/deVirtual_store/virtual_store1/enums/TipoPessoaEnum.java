package cavalcante.deVirtual_store.virtual_store1.enums;

public enum TipoPessoaEnum {

    CLIENTE("Cliente"),
    FORNECEDOR("Fornecedor"),
    EMPREGADO("Empregado");

    private String descricao;

    TipoPessoaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "TipoPessoaEnum{" +
                "descricao='" + descricao + '\'' +
                '}';
    }






}
