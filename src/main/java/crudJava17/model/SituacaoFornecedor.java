package crudJava17.model;

public enum SituacaoFornecedor {
    ATIVO("Ativo"),
    BAIXADO("Baixado"),
    SUSPENSO("Suspenso");

    private final String descricao;

    SituacaoFornecedor(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
