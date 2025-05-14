package br.com.hiquez.Controle_Rembolso_Corporativo.Exception.CadastroUsuario;

import org.springframework.stereotype.Component;

import br.com.hiquez.Controle_Rembolso_Corporativo.DTO.RegistroUsuarioDTO;
import br.com.hiquez.Controle_Rembolso_Corporativo.Repository.UsuarioRepository;

@Component
public class NomeValidador implements ValidadorCadastroUsuario {

    private final UsuarioRepository repository;

    public NomeValidador(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(RegistroUsuarioDTO dto) {
        if (repository.existsByNome(dto.nome())) {
            throw new NomeExisteException("Nome já cadastrado!");
        }
    }
}
