package br.com.hiquez.Controle_Rembolso_Corporativo.Entity;

import java.time.LocalDate;
import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.StatusPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "tb_financeiro")
public class Financeiro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @ManyToOne(targetEntity = Usuario.class)
    private Usuario usuarioResponsavel;
    @NotNull
    private StatusPagamento status;
    @NotNull
    private LocalDate dataPagamento;

}
