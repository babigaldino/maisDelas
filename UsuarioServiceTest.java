package com.delas.api.service;

import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioModel usuarioExemplo;

    @BeforeEach
    void setUp() {
        // Criando o usuário de exemplo para os testes
        usuarioExemplo = UsuarioModel.criarUsuarioExemplo();
        usuarioExemplo.setId(1L); // Definindo um ID válido para o teste
    }

    @Test
    void testSalvarUsuario() {
        // Simula o comportamento do repository para salvar um novo usuário
        when(usuarioRepository.save(usuarioExemplo)).thenReturn(usuarioExemplo);

        // Chama o método de salvar
        UsuarioModel usuarioSalvo = usuarioService.salvarUsuario(usuarioExemplo);

        // Verifica se o usuário foi salvo corretamente
        assertNotNull(usuarioSalvo);
        assertEquals(usuarioExemplo.getNome(), usuarioSalvo.getNome());
        assertEquals(usuarioExemplo.getEmail(), usuarioSalvo.getEmail());

        // Verifica se o método save() foi chamado uma vez
        verify(usuarioRepository, times(1)).save(usuarioExemplo);
    }

    @Test
    void testBuscarUsuarioPorId() {
        // Simula o comportamento do repository para retornar um usuário pelo ID
        when(usuarioRepository.findById(usuarioExemplo.getId())).thenReturn(Optional.of(usuarioExemplo));

        // Chama o método de buscar por ID
        Optional<UsuarioModel> usuarioEncontrado = usuarioService.buscarUsuarioPorId(usuarioExemplo.getId());

        // Verifica se o usuário foi encontrado
        assertTrue(usuarioEncontrado.isPresent());
        assertEquals(usuarioExemplo.getNome(), usuarioEncontrado.get().getNome());

        // Verifica se o método findById() foi chamado uma vez
        verify(usuarioRepository, times(1)).findById(usuarioExemplo.getId());
    }

    @Test
    void testBuscarUsuarioPorIdNaoEncontrado() {
        // Simula o comportamento do repository para não encontrar o usuário pelo ID
        when(usuarioRepository.findById(usuarioExemplo.getId())).thenReturn(Optional.empty());

        // Chama o método de buscar por ID
        Optional<UsuarioModel> usuarioNaoEncontrado = usuarioService.buscarUsuarioPorId(usuarioExemplo.getId());

        // Verifica se o usuário não foi encontrado
        assertFalse(usuarioNaoEncontrado.isPresent());

        // Verifica se o método findById() foi chamado uma vez
        verify(usuarioRepository, times(1)).findById(usuarioExemplo.getId());
    }

    @Test
    void testAtualizarUsuario() {
        // Cria um novo usuário para atualizar
        UsuarioModel usuarioAtualizado = new UsuarioModel();
        usuarioAtualizado.setNome("João Silva Atualizado");
        usuarioAtualizado.setEmail("joao.silva.atualizado@email.com");
        usuarioAtualizado.setSenha("senha456");
        usuarioAtualizado.setTelefone("9876543210");
        usuarioAtualizado.setTipo(UsuarioModel.TipoUsuario.PRESTADOR);
        usuarioAtualizado.setRua("Rua Atualizada");
        usuarioAtualizado.setBairro("Bairro Novo");
        usuarioAtualizado.setCep("54321-678");
        usuarioAtualizado.setDataCriacao(usuarioExemplo.getDataCriacao());
        usuarioAtualizado.setCpf("987.654.321-00");

        // Simula o comportamento do repository para encontrar o usuário pelo ID
        when(usuarioRepository.findById(usuarioExemplo.getId())).thenReturn(Optional.of(usuarioExemplo));

        // Simula o comportamento do repository para salvar o usuário atualizado
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioAtualizado); // Usando "any()" para evitar problemas de referência

        // Chama o método de atualizar
        UsuarioModel usuarioAtualizadoSalvo = usuarioService.atualizarUsuario(usuarioExemplo.getId(), usuarioAtualizado);

        // Verifica se o usuário foi atualizado corretamente
        assertNotNull(usuarioAtualizadoSalvo);
        assertEquals(usuarioAtualizado.getNome(), usuarioAtualizadoSalvo.getNome());
        assertEquals(usuarioAtualizado.getEmail(), usuarioAtualizadoSalvo.getEmail());

        // Verifica se o método save() foi chamado uma vez com o objeto atualizado
        verify(usuarioRepository, times(1)).save(any(UsuarioModel.class)); // Verificando com any()

        // Verifica se o método findById() foi chamado uma vez
        verify(usuarioRepository, times(1)).findById(usuarioExemplo.getId());
    }


    @Test
    void testDeletarUsuario() {
        // Simula o comportamento do repository para deletar o usuário
        doNothing().when(usuarioRepository).deleteById(usuarioExemplo.getId());

        // Chama o método de deletar
        boolean usuarioDeletado = usuarioService.deletarUsuario(usuarioExemplo.getId());

        // Verifica se o usuário foi deletado
        assertTrue(usuarioDeletado);

        // Verifica se o método deleteById() foi chamado uma vez
        verify(usuarioRepository, times(1)).deleteById(usuarioExemplo.getId());
    }
}
