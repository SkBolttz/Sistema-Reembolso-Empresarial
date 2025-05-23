package br.com.hiquez.Controle_Rembolso_Corporativo.Exception.CadastroUsuario;

public class CpfExisteException extends RuntimeException {

    public CpfExisteException(String msg) {
        super(msg);
    }
}
