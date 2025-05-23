package br.com.hiquez.Controle_Rembolso_Corporativo.DTO;

import java.time.LocalDate;
import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.CategoriaReembolso;
import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.StatusPagamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReembolsoResponseDTO {

    private Long id;
    private String nomeSolicitante;
    private String responsavel;
    private String justificativaSolicitante;
    private String justificativaResponsavel;
    private LocalDate dataValorGasto;
    private LocalDate dataAlteracao;
    private LocalDate dataAbertura;
    private double valorReembolso;
    private CategoriaReembolso categoriaReembolso;
    private String comprovante;
    private StatusPagamento status;

    public ReembolsoResponseDTO(Long id, String nomeSolicitante, String responsavel,
            String justificativaSolicitante, String justificativaResponsavel,
            LocalDate dataValorGasto, LocalDate dataAlteracao,
            LocalDate dataAbertura, double valorReembolso,
            CategoriaReembolso categoriaReembolso, String comprovante,
            StatusPagamento status) {
        this.id = id;
        this.nomeSolicitante = nomeSolicitante;
        this.responsavel = responsavel;
        this.justificativaSolicitante = justificativaSolicitante;
        this.justificativaResponsavel = justificativaResponsavel;
        this.dataValorGasto = dataValorGasto;
        this.dataAlteracao = dataAlteracao;
        this.dataAbertura = dataAbertura;
        this.valorReembolso = valorReembolso;
        this.categoriaReembolso = categoriaReembolso;
        this.comprovante = comprovante;
        this.status = status;
    }
}