package br.com.hiquez.Controle_Rembolso_Corporativo.DTO;

import jakarta.validation.constraints.NotBlank;

public record ResponsavelReembolsoDTO(
        @NotBlank String nomeSolicitante) {

}
