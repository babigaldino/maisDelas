package com.delas.api.repository;

import com.delas.api.model.UsuarioModel;
import com.delas.api.model.UsuarioModel.TipoUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve salvar um usuário no banco de dados")
    void SalvarUsuario() {
        UsuarioModel usuario = new UsuarioModel(
                null, // O ID será gerado automaticamente
                "Maria Silva",
                "maria@email.com",
                "senha123",
                "81999999999",
                TipoUsuario.CLIENTE,
                "Rua das Flores",
                "Centro",
                "50000000",
               new Date(),
                "12345678900"
        );

        UsuarioModel usuarioSalvo = usuarioRepository.save(usuario);

        assertThat(usuarioSalvo).isNotNull();
        assertThat(usuarioSalvo.getId()).isNotNull();
        assertThat(usuarioSalvo.getNome()).isEqualTo("Maria Silva");
    }

    @Test
    @DisplayName("Deve buscar um usuário pelo ID")
    void BuscarUsuarioPorId() {
        UsuarioModel usuario = new UsuarioModel(
                null,
                "João Santos",
                "joao@email.com",
                "senha456",
                "81988888888",
                TipoUsuario.PRESTADOR,
                "Rua do Sol",
                "Boa Viagem",
                "51000000",
                new Date(),
                "98765432100"
        );

        UsuarioModel usuarioSalvo = usuarioRepository.save(usuario);

        Optional<UsuarioModel> usuarioEncontrado = usuarioRepository.findById(usuarioSalvo.getId());

        assertThat(usuarioEncontrado).isPresent();
        assertThat(usuarioEncontrado.get().getNome()).isEqualTo("João Santos");
    }

    @Test
    @DisplayName("Deve deletar um usuário pelo ID")
    void DeletarUsuario() {
        UsuarioModel usuario = new UsuarioModel(
                null,
                "Ana Souza",
                "ana@email.com",
                "senha789",
                "81977777777",
                TipoUsuario.ADMIN,
                "Rua Alegre",
                "Madalena",
                "52000000",
              new Date(),
                "11223344556"
        );

        UsuarioModel usuarioSalvo = usuarioRepository.save(usuario);

        usuarioRepository.deleteById(usuarioSalvo.getId());

        Optional<UsuarioModel> usuarioDeletado = usuarioRepository.findById(usuarioSalvo.getId());
        assertThat(usuarioDeletado).isEmpty();
    }

    @Test
    @DisplayName("Deve atualizar informações de um usuário existente")
    void AtualizarUsuario() {
        UsuarioModel usuario = new UsuarioModel(
                null,
                "Carlos Lima",
                "carlos@email.com",
                "senha111",
                "81966666666",
                TipoUsuario.CLIENTE,
                "Rua Nova",
                "Recife Antigo",
                "53000000",
                new Date(),
                "99887766554"
        );

        UsuarioModel usuarioSalvo = usuarioRepository.save(usuario);


        usuarioSalvo.setNome("Carlos Alberto Lima");
        usuarioSalvo.setEmail("carlos.alberto@email.com");
        UsuarioModel usuarioAtualizado = usuarioRepository.save(usuarioSalvo);

        assertThat(usuarioAtualizado.getNome()).isEqualTo("Carlos Alberto Lima");
        assertThat(usuarioAtualizado.getEmail()).isEqualTo("carlos.alberto@email.com");
    }
}
