package br.com.hiquez.Controle_Rembolso_Corporativo.DTO;

import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.StatusPagamento;

public record AtualizarStatusReembolsoDTO(
        long id,
        String responsavel,
        String justificativaResponsavel,
        StatusPagamento status) {

}
