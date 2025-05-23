package br.com.hiquez.Controle_Rembolso_Corporativo.Exception.Security;

public class ValidarTokenException extends RuntimeException {

    public ValidarTokenException(String msg) {
        super(msg);
    }
}
