package br.com.hiquez.Controle_Rembolso_Corporativo.Entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.CategoriaReembolso;
import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.StatusPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_solicitacao")
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    @JsonIgnore
    private Usuario usuarioSolicitante;

    @ManyToOne
    @JsonIgnore
    private Usuario usuarioResponsavel;

    @NotNull
    private double valorReembolso;

    @NotBlank
    private String justificativaSolicitante;

    private String justificativaResponsavel;

    @NotNull
    private LocalDate dataValorGasto;

    private LocalDate dataAlteracao;

    @NotNull
    private LocalDate dataAbertura;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CategoriaReembolso categoriaReembolso;

    @NotBlank
    private String comprovante;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    public Solicitacao(Usuario usuarioSolicitante, String justificativaSolicitante,
            LocalDate dataAbertura, CategoriaReembolso categoriaReembolso,
            String comprovante, double valorReembolso, StatusPagamento status, LocalDate dataValorGasto) {
        this.usuarioSolicitante = usuarioSolicitante;
        this.justificativaSolicitante = justificativaSolicitante;
        this.dataValorGasto = dataValorGasto;
        this.categoriaReembolso = categoriaReembolso;
        this.comprovante = comprovante;
        this.valorReembolso = valorReembolso;
        this.status = status;
        this.dataAbertura = dataAbertura;
    }
}
