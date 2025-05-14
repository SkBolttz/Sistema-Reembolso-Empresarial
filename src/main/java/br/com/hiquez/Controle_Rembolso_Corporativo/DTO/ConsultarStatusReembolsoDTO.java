package br.com.hiquez.Controle_Rembolso_Corporativo.DTO;

import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.StatusPagamento;

public record ConsultarStatusReembolsoDTO(
    StatusPagamento status
) {
    
}
