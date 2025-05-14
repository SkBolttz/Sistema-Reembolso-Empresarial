package br.com.hiquez.Controle_Rembolso_Corporativo.Exception.CadastroUsuario;

public class CadastroException extends RuntimeException{
    
    public CadastroException(String msg){
        throw new RuntimeException("Erro ao cadastrar usuário! Verifique os dados e tente novamente! " + msg);
    }
}
