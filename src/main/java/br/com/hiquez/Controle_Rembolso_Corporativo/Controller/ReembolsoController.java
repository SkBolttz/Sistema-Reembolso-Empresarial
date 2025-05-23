package br.com.hiquez.Controle_Rembolso_Corporativo.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
import br.com.hiquez.Controle_Rembolso_Corporativo.Exception.Reembolso.ReembolsoException;
import br.com.hiquez.Controle_Rembolso_Corporativo.Service.SolicitacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reembolso")
@Tag(name = "Reembolso", description = "Controller responsavel pelo controle e gerenciamento de reembolsos para usuarios.")
public class ReembolsoController {

    @Autowired
    private SolicitacaoService service;

    @Operation(summary = "Realiza o cadastro de um reembolso.", description = "Cria um novo reembolso e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reembolso cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar reembolso.")
    })
    @PostMapping("/solicitar")
    public ResponseEntity<Map<String, String>> solicitarReembolso(
            @RequestPart("dto") @Valid SolicitarReembolsoDTO dto,
            @RequestPart("comprovante") MultipartFile comprovante) throws IOException {

        try {
            service.solicitarReembolso(dto, comprovante);
            return ResponseEntity.status(201).body(Map.of("mensagem", "Reembolso solicitado com sucesso!"));
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(Map.of("mensagem", e.getMessage()));
        }
    }

    @Operation(summary = "Verifica os reembolsos pendetes de um usuario.", description = "Verifica os reembolsos pendentes de um usuario e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Verifica os reembolsos pendentes de um usuario."),
            @ApiResponse(responseCode = "400", description = "Erro ao verificar reembolso.")
    })
    @PostMapping("/meusReembolsos")
    public ResponseEntity<List<Solicitacao>> verificarMeusReembolso(@RequestBody @Valid ResponsavelReembolsoDTO dto) {

        try {
            return ResponseEntity.status(200).body(service.verificarReembolso(dto));
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "Verifica todos os reembolsos pendentes.", description = "Verifica todos os reembolsos pendentes e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Verifica todos os reembolsos pendentes."),
            @ApiResponse(responseCode = "400", description = "Erro ao verificar reembolso.")
    })
    @PostMapping("/todosAbertos")
    public ResponseEntity<List<Solicitacao>> verificarTodosReembolsos(@RequestBody @Valid ResponsavelDTO dto) {

        try {
            return ResponseEntity.status(200).body(service.verificarTodosReembolsos(dto));
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "Altera o status de um reembolso.", description = "Altera o status de um reembolso e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alteracao de status feita com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao alterar status.")
    })
    @PutMapping("/alterarStatusReembolso")
    public ResponseEntity<Map<String, String>> recusarReembolso(@RequestBody @Valid AtualizarStatusReembolsoDTO dto) {

        try {
            return ResponseEntity.status(200)
                    .body(Map.of("mensagem", "" + service.alterarStatusReembolso(dto)));
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(Map.of("mensagem", "Nenhum reembolso pendente!"));
        }
    }

    @Operation(summary = "Verifica os reembolsos por status.", description = "Verifica os reembolsos por status e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Verifica os reembolsos por status."),
            @ApiResponse(responseCode = "400", description = "Erro ao verificar reembolso.")
    })
    @PostMapping("/consultarPorStatus")
    public ResponseEntity<List<Solicitacao>> consultarReembolsoPorStatus(
            @RequestBody @Valid ConsultarStatusReembolsoDTO dto) {

        try {
            return ResponseEntity.status(200).body(service.consultarReembolsoPorStatus(dto));
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "Verifica os reembolsos concluidos.", description = "Verifica os reembolsos concluidos e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Verifica os reembolsos concluidos."),
            @ApiResponse(responseCode = "400", description = "Erro ao verificar reembolso.")
    })
    @PostMapping("/reembolsosConcluidos")
    public ResponseEntity<List<ReembolsoCompletoDTO>> verificarReembolsoConcluido(
            @RequestBody @Valid ResponsavelReembolsoDTO dto) {

        try {
            return ResponseEntity.status(200).body(service.verificarReembolsoConcluidos(dto));
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "Lista todos os reembolsos pendentes para atualizar.", description = "Lista todos os reembolsos pendentes para atualizar e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Listagem de reembolsos pendentes."),
            @ApiResponse(responseCode = "400", description = "Erro ao listar reembolso.")
    })
    @PostMapping("/ReembolsoAtualizar")
    public ResponseEntity<List<Solicitacao>> ReembolsoAtualizar(@RequestBody @Valid ReembolsoAtualizarDTO dto) {

        try {
            return ResponseEntity.status(200).body(service.ReembolsoAtualizar(dto));
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "Verificar usuario solicitante.", description = "Verifica se o usuario existe e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario verificado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao verificar usuario.")
    })
    @PostMapping("/verificarSolicitante")
    public ResponseEntity<String> verificarSolicitante(@RequestBody ReembolsoAtualizarDTO dto) {
        String nome = service.verificarSolicitante(dto);
        return ResponseEntity.ok().body(nome);
    }

    @Operation(summary = "Verifica os reembolsos pendentes.", description = "Verifica os reembolsos pendentes e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Verifica os reembolsos pendentes."),
            @ApiResponse(responseCode = "400", description = "Erro ao verificar reembolso.")
    })
    @PostMapping("/reembolsosPendentes")
    public ResponseEntity<?> verificarReembolsoPendentes(@RequestBody @Valid ResponsavelReembolsoDTO dto) {
        try {
            List<ReembolsoResponseDTO> resposta = service.verificarReembolsoPendentes(dto);
            return ResponseEntity.ok(resposta);
        } catch (ReembolsoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Verifica os reembolsos recusados.", description = "Verifica os reembolsos recusados e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Verifica os reembolsos recusados."),
            @ApiResponse(responseCode = "400", description = "Erro ao verificar reembolso.")
    })
    @PostMapping("/reembolsosRecusados")
    public ResponseEntity<List<ReembolsoCompletoDTO>> verificarReembolsoRecusado(
            @RequestBody @Valid ResponsavelReembolsoDTO dto) {

        try {
            return ResponseEntity.status(200).body(service.verificarReembolsoRecusados(dto));
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "Verificar todos os reembolsos ja criados.", description = "Verifica todos os reembolsos ja criados e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Verifica todos os reembolsos ja criados."),
            @ApiResponse(responseCode = "400", description = "Erro ao verificar reembolso.")
    })
    @GetMapping("/todos")
    public ResponseEntity<List<ReembolsoCompletoDTO>> verificarReembolsoTodos() {

        try {
            return ResponseEntity.status(200).body(service.verificarReembolsoTodos());
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
