package br.com.hiquez.Controle_Rembolso_Corporativo.Controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.AtualizarStatusReembolsoDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.ConsultarStatusReembolsoDTO;
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
    public ResponseEntity<Map<String, String>> solicitarReembolso(@RequestBody @Valid SolicitarReembolsoDTO dto) {

        try {
            service.solicitarReembolso(dto);
            return ResponseEntity.status(201).body(Map.of("mensagem", "Reembolso solicitado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("mensagem", e.getMessage()));
        }
    }

    @Operation(summary = "Realiza o cadastro de um reembolso.", description = "Cria um novo reembolso e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reembolso cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar reembolso.")
    })
    @GetMapping("/meusReembolsos")
    public ResponseEntity<List<Solicitacao>> verificarMeusReembolso(@RequestBody @Valid ResponsavelReembolsoDTO dto) {

        try {
            return ResponseEntity.status(200).body(service.verificarReembolso(dto));
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "Realiza o cadastro de um reembolso.", description = "Cria um novo reembolso e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reembolso cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar reembolso.")
    })
    @GetMapping("/todosAbertos")
    public ResponseEntity<List<Solicitacao>> verificarTodosReembolsos() {

        try {
            return ResponseEntity.status(200).body(service.verificarTodosReembolsos());
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "Realiza o cadastro de um reembolso.", description = "Cria um novo reembolso e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reembolso cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar reembolso.")
    })
    @PutMapping("/alterarStatusReembolso")
    public ResponseEntity<Map<String, String>> recusarReembolso(@RequestBody @Valid AtualizarStatusReembolsoDTO dto) {

        try {
            return ResponseEntity.status(200)
                    .body(Map.of("mensagem", "Rembolsos Pendentes: " + service.alterarStatusReembolso(dto)));
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(Map.of("mensagem", "Nenhum reembolso pendente!"));
        }
    }

    @Operation(summary = "Realiza o cadastro de um reembolso.", description = "Cria um novo reembolso e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reembolso cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar reembolso.")
    })
    @GetMapping("/consultarPorStatus")
    public ResponseEntity<List<Solicitacao>> consultarReembolsoPorStatus(
            @RequestBody @Valid ConsultarStatusReembolsoDTO dto) {

        try {
            return ResponseEntity.status(200).body(service.consultarReembolsoPorStatus(dto));
        } catch (ReembolsoException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
