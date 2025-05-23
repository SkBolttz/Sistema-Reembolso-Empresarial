package br.com.hiquez.Controle_Rembolso_Corporativo.DTO;

import java.time.LocalDate;

import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.CategoriaReembolso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitarReembolsoDTO(
        @NotBlank String nomeSolicitante,
        @NotNull double valorReembolso,
        @NotBlank String justificativa,
        @NotNull CategoriaReembolso categoriaReembolso,
        @NotNull LocalDate dataValorGasto) {
}
