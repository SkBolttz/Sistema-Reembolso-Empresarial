package br.com.hiquez.Controle_Rembolso_Corporativo.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.CategoriaReembolso;
import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.StatusPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_solicitacao")
public class Solicitacao {
    
    public Solicitacao(Usuario userExists, String justificativa, LocalDateTime now,
            CategoriaReembolso categoriaReembolso, String comprovante,
            double valorReembolso, StatusPagamento status, LocalDate dataValorGasto) {
        this.usuarioSolicitante = userExists;
        this.justificativaSolicitante = justificativa;
        this.dataValorGasto = dataValorGasto;
        this.categoriaReembolso = categoriaReembolso;
        this.comprovante = comprovante;
        this.valorReembolso = valorReembolso;
        this.status = status;
        this.dataAbertura = now.toLocalDate();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @ManyToOne(targetEntity = Usuario.class)
    private Usuario usuarioSolicitante;
    @ManyToOne(targetEntity = Usuario.class)
    private Usuario usuarioGestorResponsavel;
    @ManyToOne(targetEntity = Usuario.class)
    private Usuario usuarioFinanceiroResponsavel;
    @NotNull
    private double valorReembolso;
    @NotBlank
    private String justificativaSolicitante;
    @NotBlank
    private String justificativaResponsavel;
    @NotNull
    private LocalDate dataValorGasto;
    @NotNull
    private LocalDate dataAlteracao;
    @NotNull
    private LocalDate dataAbertura;
    @NotNull
    private CategoriaReembolso categoriaReembolso;
    @NotBlank
    private String comprovante;
    @NotNull
    private StatusPagamento status;

    @Override
    public String toString() {
        return "Solicitacao{" +
                "id=" + id +
                ", usuarioSolicitante=" + usuarioSolicitante +
                ", usuarioGestorResponsavel=" + usuarioGestorResponsavel +
                ", usuarioFinanceiroResponsavel=" + usuarioFinanceiroResponsavel +
                ", valorReembolso=" + valorReembolso +
                ", justificativa='" + justificativaSolicitante + '\'' +
                ", data valor gasto=" + dataValorGasto +
                ", dataAprovacao=" + dataAlteracao +
                ", dataAbertura=" + dataAbertura +
                ", categoriaReembolso=" + categoriaReembolso +
                ", comprovante='" + comprovante + '\'' +
                ", status=" + status +
                '}';
    }
}
