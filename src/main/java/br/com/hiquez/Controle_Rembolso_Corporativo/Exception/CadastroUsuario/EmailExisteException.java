package br.com.hiquez.Controle_Rembolso_Corporativo.Exception.CadastroUsuario;

public class EmailExisteException extends RuntimeException{

    public EmailExisteException(String msg){
        super(msg);
    }
}
