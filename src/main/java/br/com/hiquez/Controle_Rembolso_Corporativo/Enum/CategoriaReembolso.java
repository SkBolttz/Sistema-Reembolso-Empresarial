package br.com.hiquez.Controle_Rembolso_Corporativo.Enum;

public enum CategoriaReembolso {

    ALIMENTACAO("Alimentação"),
    TRANSPORTE("Transporte"),
    COMBUSTIVEL("Combustível"),
    SAUDE("Saúde"),
    EDUCACAO("Educação"),
    LAZER("Lazer"),
    OUTROS("Outros");

    private String descricao;

    private CategoriaReembolso(String descricao) {
        this.descricao = descricao;
    }
}
