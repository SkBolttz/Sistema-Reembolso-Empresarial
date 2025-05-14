package br.com.hiquez.Controle_Rembolso_Corporativo.Exception.CadastroUsuario;

import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.RegistroUsuarioDTO;

public interface ValidadorCadastroUsuario {
    
    void validar(RegistroUsuarioDTO dto);
}
