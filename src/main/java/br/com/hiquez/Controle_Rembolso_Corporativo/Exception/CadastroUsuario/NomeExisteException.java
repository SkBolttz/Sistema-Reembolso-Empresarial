package br.com.hiquez.Controle_Rembolso_Corporativo.Exception.CadastroUsuario;

public class NomeExisteException extends RuntimeException {

    public NomeExisteException(String msg) {
        super(msg);
    }
}
