package br.com.hiquez.Controle_Rembolso_Corporativo.Enum;

public enum StatusPagamento {

    PAGO("Pago"),
    PENDENTE("Pendente"),
    RECUSADO("Recusado"),
    CANCELADO("Cancelado");

    private String descricao;

    private StatusPagamento(String descricao) {
        this.descricao = descricao;
    }
}
