package br.com.hiquez.Controle_Rembolso_Corporativo.Enum;

public enum TipoUsuario {
    
    FUNCIONARIO("Funcionário"),
    GESTOR("Gestor"),
    FINANCEIRO("Financeiro");
    
    private String descricao;
    
    private TipoUsuario(String descricao) {
        this.descricao = descricao;
    }
}
