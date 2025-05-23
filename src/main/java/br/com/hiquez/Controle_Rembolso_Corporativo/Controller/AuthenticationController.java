package br.com.hiquez.Controle_Rembolso_Corporativo.Controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.LoginUsuarioDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.RegistroUsuarioDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.Entity.Usuario;
import br.com.hiquez.Controle_Rembolso_Corporativo.Exception.CadastroUsuario.CadastroException;
import br.com.hiquez.Controle_Rembolso_Corporativo.Security.AuthenticationService;
import br.com.hiquez.Controle_Rembolso_Corporativo.Security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Controller responsavel pelo controle e gerenciamento de autenticao para usuarios.")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @Operation(summary = "Realiza o login de um usuário.", description = "Autentica o usuário usando email e senha, e retorna um token JWT caso as credenciais estejam corretas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Usuário ou senha incorretos.")
    })
    @PostMapping("/login/usuario")
    public ResponseEntity<String> logar(@RequestBody @Valid LoginUsuarioDTO usuario) {
        try {
            var tokenAuthentication = new UsernamePasswordAuthenticationToken(usuario.nome(), usuario.senha());
            var authentication = manager.authenticate(tokenAuthentication);

            var user = (Usuario) authentication.getPrincipal();
            String token = tokenService.gerarToken(user, user.getTipo().toString());
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha incorretos.");
        }
    }

    @Operation(summary = "Realiza o cadastro de um usuário.", description = "Cria um novo usuário e retorna uma mensagem de sucesso ou um erro caso o cadastro falhe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar usuário.")
    })
    @PostMapping("/registrar/usuario")
    public ResponseEntity<Map<String, String>> registrar(@RequestBody @Valid RegistroUsuarioDTO dto) {
        try {
            return ResponseEntity.status(201).body(Map.of("mensagem", service.registrar(dto)));
        } catch (CadastroException e) {
            return ResponseEntity.status(400).body(Map.of("mensagem", e.getMessage()));
        }
    }
}
