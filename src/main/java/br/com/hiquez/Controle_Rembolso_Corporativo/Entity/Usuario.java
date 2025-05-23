package br.com.hiquez.Controle_Rembolso_Corporativo.Entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.hiquez.Controle_Rembolso_Corporativo.Enum.TipoUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(min = 10, max = 40, message = "O nome deve ter entre 10 e 40 caracteres")
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    @NotBlank
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String senha;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    @OneToMany(mappedBy = "usuarioSolicitante")
    private List<Solicitacao> solicitacoes;

    @OneToMany(mappedBy = "usuarioResponsavel")
    @JsonManagedReference
    private List<Solicitacao> solicitacoesComoResponsavel;

    public Usuario(String nome, String email, String cpf, String senha, TipoUsuario verificarTipo) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.tipo = verificarTipo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.tipo == null) {
            throw new IllegalStateException("Tipo de usuário não pode ser nulo.");
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.tipo.name().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return this.getSenha();
    }

    @Override
    public String getUsername() {
        return this.getNome();
    }

    @Override
    public String toString() {
        return "Nome do Usuário: " + this.getNome() +
                " Email: " + this.getEmail() +
                " CPF: " + this.getCpf() +
                " Tipo de Usuário: " + this.getTipo();
    }
}
