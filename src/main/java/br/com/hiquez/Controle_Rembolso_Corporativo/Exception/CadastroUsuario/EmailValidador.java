package br.com.hiquez.Controle_Rembolso_Corporativo.Exception.CadastroUsuario;

import org.springframework.stereotype.Component;

import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.RegistroUsuarioDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.Repository.UsuarioRepository;

@Component
public class EmailValidador implements ValidadorCadastroUsuario {

    private final UsuarioRepository repository;

    public EmailValidador(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(RegistroUsuarioDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new EmailExisteException("Email já cadastrado!");
        }
    }
}
