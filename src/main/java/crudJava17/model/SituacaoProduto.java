package crudJava17.model;

public enum SituacaoProduto {
    ATIVO("Ativo"),
    INATIVO("inativo");

    private final String descricao;

    SituacaoProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
