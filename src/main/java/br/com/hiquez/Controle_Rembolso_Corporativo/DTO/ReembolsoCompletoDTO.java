package br.com.hiquez.Controle_Rembolso_Corporativo.DTO;

import br.com.hiquez.Controle_Rembolso_Corporativo.Entity.Solicitacao;

public record ReembolsoCompletoDTO(
        Long id,
        String nomeSolicitante,
        String nomeResponsavel,
        Double valorReembolso,
        String categoriaReembolso,
        String dataValorGasto,
        String justificativaResponsavel,
        String comprovante,
        String status,
        String dataAbertura,
        String dataAprovacao) {
    public ReembolsoCompletoDTO(Solicitacao s) {
        this(
                s.getId(),
                s.getUsuarioSolicitante().getNome(),
                s.getUsuarioResponsavel() != null ? s.getUsuarioResponsavel().getNome() : "Não atribuído",
                s.getValorReembolso(),
                s.getCategoriaReembolso().toString(),
                s.getDataValorGasto().toString(),
                s.getJustificativaResponsavel(),
                s.getComprovante(),
                s.getStatus().toString(),
                s.getDataAbertura().toString(),
                s.getDataAlteracao() != null ? s.getDataAlteracao().toString() : "");
    }
}
