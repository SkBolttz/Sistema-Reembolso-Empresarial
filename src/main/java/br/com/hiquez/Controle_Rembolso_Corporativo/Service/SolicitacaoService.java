package br.com.hiquez.Controle_Rembolso_Corporativo.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.AtualizarStatusReembolsoDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.ConsultarStatusReembolsoDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.ReembolsoAtualizarDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.ReembolsoCompletoDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.ReembolsoResponseDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.ResponsavelDTO;
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

    private final UsuarioRepository usuarioRepository;
    private final SolicitacaoRepository solicitacaoRepository;

    private final Path uploadDir = Paths.get("uploads/comprovantes");

    public SolicitacaoService(UsuarioRepository usuarioRepository, SolicitacaoRepository solicitacaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.solicitacaoRepository = solicitacaoRepository;

        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar diretório de upload", e);
        }
    }

    public void solicitarReembolso(SolicitarReembolsoDTO dto, MultipartFile comprovante) throws IOException {
        Usuario userExists = usuarioRepository.findByNome(dto.nomeSolicitante());
        if (userExists == null) {
            throw new UsuarioException("Usuário não encontrado!");
        }

        if (dto.valorReembolso() <= 0) {
            throw new ReembolsoException("O valor do reembolso não pode ser menor ou igual a zero!");
        }

        if (dto.dataValorGasto().isAfter(LocalDate.now())) {
            throw new ReembolsoException("A data do valor do gasto não pode ser maior que a data atual!");
        }

        String nomeOriginal = comprovante.getOriginalFilename();
        if (nomeOriginal == null || nomeOriginal.isBlank()) {
            throw new ReembolsoException("Arquivo comprovante inválido!");
        }
        String extensao = "";
        int i = nomeOriginal.lastIndexOf('.');
        if (i > 0) {
            extensao = nomeOriginal.substring(i);
        }
        String nomeArquivo = UUID.randomUUID() + extensao;

        Path destinoArquivo = uploadDir.resolve(nomeArquivo);
        Files.copy(comprovante.getInputStream(), destinoArquivo, StandardCopyOption.REPLACE_EXISTING);

        Solicitacao solicitacao = new Solicitacao(
                userExists,
                dto.justificativa(),
                LocalDate.now(),
                dto.categoriaReembolso(),
                nomeArquivo,
                dto.valorReembolso(),
                StatusPagamento.PENDENTE,
                dto.dataValorGasto());

        solicitacaoRepository.save(solicitacao);
    }

    public List<Solicitacao> verificarReembolso(ResponsavelReembolsoDTO dto) {
        return solicitacaoRepository.findAll()
                .stream()
                .filter(e -> e.getUsuarioSolicitante().getNome().equals(dto.nomeSolicitante())
                        && e.getStatus() != StatusPagamento.PAGO)
                .toList();
    }

    public List<Solicitacao> verificarTodosReembolsos(ResponsavelDTO dto) {
        return solicitacaoRepository.findAll()
                .stream()
                .filter(e -> e.getStatus() != StatusPagamento.CANCELADO && e.getStatus() != StatusPagamento.PAGO
                        && !e.getUsuarioSolicitante().getNome().equals(dto.responsavel()))
                .toList();
    }

    public String alterarStatusReembolso(AtualizarStatusReembolsoDTO dto) {
        Usuario userExists = usuarioRepository.findByNome(dto.responsavel());

        if (userExists == null) {
            throw new UsuarioException("Usuário não encontrado!");
        }

        if (userExists.getTipo() == TipoUsuario.FUNCIONARIO) {
            throw new ReembolsoException("Usuário não pode alterar status do reembolso!");
        }

        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new ReembolsoException("Nenhum reembolso pendente!"));

        solicitacao.setStatus(dto.status());
        solicitacao.setJustificativaResponsavel(dto.justificativaResponsavel());
        solicitacao.setDataAlteracao(LocalDate.now());
        solicitacao.setUsuarioResponsavel(userExists);

        solicitacaoRepository.save(solicitacao);

        return switch (dto.status()) {
            case PAGO -> "Reembolso pago com sucesso!";
            case CANCELADO -> "Reembolso cancelado com sucesso!";
            case RECUSADO -> "Reembolso recusado com sucesso!";
            default -> "Reembolso pendente!";
        };
    }

    public List<Solicitacao> consultarReembolsoPorStatus(ConsultarStatusReembolsoDTO dto) {
        return solicitacaoRepository.findAll()
                .stream()
                .filter(e -> e.getStatus().equals(dto.status())
                        && e.getUsuarioSolicitante().getNome().equals(dto.nomeSolicitante()))
                .toList();
    }

    public List<ReembolsoCompletoDTO> verificarReembolsoConcluidos(ResponsavelReembolsoDTO dto) {
        return solicitacaoRepository.findAll().stream()
                .filter(e -> e.getUsuarioSolicitante().getNome().equals(dto.nomeSolicitante())
                        && e.getStatus() == StatusPagamento.PAGO)
                .map(ReembolsoCompletoDTO::new)
                .toList();
    }

    public List<Solicitacao> ReembolsoAtualizar(ReembolsoAtualizarDTO dto) {
        return solicitacaoRepository.findAll()
                .stream()
                .filter(e -> e.getId() == dto.idReembolso()
                        && e.getStatus() != StatusPagamento.PAGO)
                .toList();
    }

    public String verificarSolicitante(ReembolsoAtualizarDTO dto) {
        return solicitacaoRepository.findById(dto.idReembolso())
                .stream()
                .filter(e -> e.getId() == dto.idReembolso())
                .map(e -> e.getUsuarioSolicitante().getNome())
                .findFirst()
                .orElse(null);
    }

    public List<ReembolsoResponseDTO> verificarReembolsoPendentes(ResponsavelReembolsoDTO dto) {
        return solicitacaoRepository.findAll()
                .stream()
                .filter(e -> e.getStatus() == StatusPagamento.PENDENTE
                        && e.getUsuarioSolicitante().getNome().equals(dto.nomeSolicitante()))
                .map(e -> new ReembolsoResponseDTO(
                        e.getId(),
                        e.getUsuarioSolicitante().getNome(),
                        e.getUsuarioResponsavel() != null ? e.getUsuarioResponsavel().getNome() : null,
                        e.getJustificativaSolicitante(),
                        e.getJustificativaResponsavel(),
                        e.getDataValorGasto(),
                        e.getDataAlteracao(),
                        e.getDataAbertura(),
                        e.getValorReembolso(),
                        e.getCategoriaReembolso(),
                        e.getComprovante(),
                        e.getStatus()))
                .toList();
    }

    public List<ReembolsoCompletoDTO> verificarReembolsoRecusados(ResponsavelReembolsoDTO dto) {
        return solicitacaoRepository.findAll().stream()
                .filter(e -> e.getUsuarioSolicitante().getNome().equals(dto.nomeSolicitante())
                        && e.getStatus() == StatusPagamento.RECUSADO)
                .map(ReembolsoCompletoDTO::new)
                .toList();
    }

    public List<ReembolsoCompletoDTO> verificarReembolsoTodos() {
        return solicitacaoRepository.findAll().stream()
                .filter(e -> e.getStatus() == StatusPagamento.RECUSADO || e.getStatus() == StatusPagamento.PAGO
                        || e.getStatus() == StatusPagamento.CANCELADO)
                .map(ReembolsoCompletoDTO::new)
                .toList();
    }
}
