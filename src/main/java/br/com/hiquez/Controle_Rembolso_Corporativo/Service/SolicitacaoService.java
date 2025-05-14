package br.com.hiquez.Controle_Rembolso_Corporativo.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.AtualizarStatusReembolsoDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.ConsultarStatusReembolsoDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.ResponsavelReembolsoDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.SolicitarReembolsoDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.Entity.Solicitacao;
import br.com.hiquez.Controle_Rembolso_Corporativo.Entity.Usuario;
import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.StatusPagamento;
import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.TipoUsuario;
import br.com.hiquez.Controle_Rembolso_Corporativo.Exception.Reembolso.ReembolsoException;
import br.com.hiquez.Controle_Rembolso_Corporativo.Exception.Reembolso.UsuarioException;
import br.com.hiquez.Controle_Rembolso_Corporativo.Repository.SolicitacaoRepository;
import br.com.hiquez.Controle_Rembolso_Corporativo.Repository.UsuarioRepository;

@Service
public class SolicitacaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;
    
    public void solicitarReembolso(SolicitarReembolsoDTO dto) {
        Usuario userExists = usuarioRepository.findByNome(dto.nomeSolicitante());

        if(userExists == null){
            throw new UsuarioException("Usuário nao encontrado!");
        }

        if(dto.valorReembolso() <= 0){
            throw new ReembolsoException("O valor do reembolso nao pode ser menor ou igual a zero!");
        }

        Solicitacao solicitacao = new Solicitacao(userExists, dto.justificativa(), LocalDateTime.now(), dto.categoriaReembolso(), dto.comprovante(), dto.valorReembolso(), StatusPagamento.PENDENTE, dto.dataValorGasto());
        solicitacaoRepository.save(solicitacao);
    }

    public List<Solicitacao> verificarReembolso(ResponsavelReembolsoDTO dto) {

        return solicitacaoRepository.findAll()
        .stream()
        .filter(e -> e.getUsuarioSolicitante().getNome().equals(dto.nomeSolicitante()))
        .toList();
    }

    public List<Solicitacao> verificarTodosReembolsos() {
        return solicitacaoRepository.findAll()
        .stream()
        .filter(e -> e.getStatus() != StatusPagamento.CANCELADO || e.getStatus() != StatusPagamento.PAGO)
        .toList();
    }

    public String alterarStatusReembolso(AtualizarStatusReembolsoDTO dto) {
       
        Usuario userExists = usuarioRepository.findByNome(dto.responsavel());

        if(userExists == null){
            throw new UsuarioException("Usuário nao encontrado!");
        }

        if(userExists.getTipo() == TipoUsuario.FUNCIONARIO){
            throw new ReembolsoException("Usuário nao pode alterar status do reembolso!");
        }

        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id()).orElseThrow(() -> new ReembolsoException("Nenhum reembolso pendente!"));

        solicitacao.setStatus(dto.status());
        solicitacao.setJustificativaResponsavel(dto.descricao());
        solicitacao.setDataValorGasto(LocalDate.now());
        
        if(userExists.getTipo() == TipoUsuario.GESTOR){
            solicitacao.setUsuarioGestorResponsavel(userExists);
        }else {
            solicitacao.setUsuarioFinanceiroResponsavel(userExists);
        }

        solicitacaoRepository.save(solicitacao);

        if(dto.status() == StatusPagamento.PAGO){
            return "Reembolso pago com sucesso!";
        } else if(dto.status() == StatusPagamento.CANCELADO){
            return "Reembolso cancelado com sucesso!";
        } else if(dto.status() == StatusPagamento.RECUSADO){
            return "Reembolso recusado com sucesso!";
        } else {
            return "Reembolso pendente!";
        }
    }

    public List<Solicitacao> consultarReembolsoPorStatus(ConsultarStatusReembolsoDTO dto) {

        return solicitacaoRepository.findAll()
        .stream()
        .filter(e -> e.getStatus() == dto.status())
        .toList();
    }
}
