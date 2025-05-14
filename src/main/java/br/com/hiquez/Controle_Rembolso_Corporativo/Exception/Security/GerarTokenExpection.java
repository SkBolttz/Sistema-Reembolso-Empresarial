package br.com.hiquez.Controle_Rembolso_Corporativo.Exception.Security;

public class GerarTokenExpection extends RuntimeException{
    
    public GerarTokenExpection(String msg){
        super(msg);
    }
}
