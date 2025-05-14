package br.com.hiquez.Controle_Rembolso_Corporativo.DTO;

import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.TipoUsuario;

public record RegistroUsuarioDTO(
        String nome,
        String email,
        String cpf,
        String senha,
        TipoUsuario tipo) {

}
