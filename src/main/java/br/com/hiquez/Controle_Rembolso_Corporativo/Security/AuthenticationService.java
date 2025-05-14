package br.com.hiquez.Controle_Rembolso_Corporativo.Security;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.RegistroUsuarioDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.Entity.Usuario;
import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.TipoUsuario;
import br.com.hiquez.Controle_Rembolso_Corporativo.Exception.CadastroUsuario.CadastroException;
import br.com.hiquez.Controle_Rembolso_Corporativo.Exception.CadastroUsuario.ValidadorCadastroUsuario;
import br.com.hiquez.Controle_Rembolso_Corporativo.Repository.UsuarioRepository;

@Service
public class AuthenticationService implements UserDetailsService {

    private final List<ValidadorCadastroUsuario> validadores;
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService token;

    @Autowired
    @Lazy
    private AuthenticationManager manager;

    public AuthenticationService(List<ValidadorCadastroUsuario> validadores, UsuarioRepository repository,
            PasswordEncoder passwordEncoder, TokenService token) {
        this.validadores = validadores;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.token = token;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario userExist = repository.findByEmail(email);

        if (userExist != null) {
            return userExist;
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }

    public String registrar(RegistroUsuarioDTO dto) {
        try {
            validadores.forEach(validador -> validador.validar(dto));

            Usuario user = new Usuario(dto.nome(), dto.email(), dto.cpf(), passwordEncoder.encode(dto.senha()),
                    verificarTipo(dto));
            Usuario salvo = repository.save(user);
            return token.gerarToken(salvo, salvo.getTipo().toString());
        } catch (CadastroException e) {
            throw new CadastroException(
                    "Erro ao cadastrar usuário! Verifique os dados e tente novamente! " + e.getMessage());
        }
    }

    private TipoUsuario verificarTipo(RegistroUsuarioDTO dto) {
        if (dto.tipo() == TipoUsuario.FINANCEIRO) {
            return TipoUsuario.FINANCEIRO;
        } else if (dto.tipo() == TipoUsuario.FUNCIONARIO) {
            return TipoUsuario.FUNCIONARIO;
        } else {
            return TipoUsuario.GESTOR;
        }
    }
}
