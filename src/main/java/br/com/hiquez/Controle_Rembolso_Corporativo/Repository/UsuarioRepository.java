package br.com.hiquez.Controle_Rembolso_Corporativo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hiquez.Controle_Rembolso_Corporativo.Entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String usernamne);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByNome(String nome);

    Usuario findByNome(String nomeSolicitante);

}
