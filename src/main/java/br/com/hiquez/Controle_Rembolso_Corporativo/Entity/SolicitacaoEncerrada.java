package br.com.hiquez.Controle_Rembolso_Corporativo.Entity;

import java.time.LocalDate;

import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.StatusPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_solicitacao_encerrada")
public class SolicitacaoEncerrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @OneToOne
    private Solicitacao solicitacao;
    @NotNull
    @ManyToOne
    private Usuario usuarioSolicitante;
    @NotNull
    @ManyToOne
    private Usuario usuarioResponsavel;
    @NotBlank
    private String justificativaResponsavel;
    @NotNull
    private LocalDate dataEncerramento;
    @NotNull
    private StatusPagamento status;
}