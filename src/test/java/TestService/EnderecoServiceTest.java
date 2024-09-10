package TestService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import app.LojaApplication;
import app.entity.Endereco;
import app.repository.EnderecoRepository;
import app.service.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojaApplication.class)
class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    private Endereco endereco;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        endereco = new Endereco(1L, "Rua A", 123, "Bairro B", "Cidade C", "Estado D", "12345-678");
    }

    @Test
    @DisplayName("findAll - deve retornar lista de endereços")
    void testFindAll() {
        when(enderecoRepository.findAll()).thenReturn(Arrays.asList(endereco));
        assertEquals(1, enderecoService.findAll().size(), "A lista de endereços deve ter 1 elemento");
    }

    @Test
    @DisplayName("findAll - deve retornar lista vazia quando nenhum endereço estiver presente")
    void testFindAllVazio() {
        when(enderecoRepository.findAll()).thenReturn(Arrays.asList());
        assertTrue(enderecoService.findAll().isEmpty(), "A lista de endereços deve estar vazia");
    }

    @Test
    @DisplayName("findAll - deve retornar lista com múltiplos endereços")
    void testFindAllMultiplo() {
        Endereco endereco2 = new Endereco(2L, "Rua B", 456, "Bairro C", "Cidade D", "Estado E", "23456-789");
        when(enderecoRepository.findAll()).thenReturn(Arrays.asList(endereco, endereco2));
        assertEquals(2, enderecoService.findAll().size(), "A lista de endereços deve ter 2 elementos");
    }

    @Test
    @DisplayName("findById - deve retornar um endereço quando encontrado")
    void testFindById() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        Optional<Endereco> resultado = enderecoService.findById(1L);
        assertTrue(resultado.isPresent(), "O endereço deve ser encontrado");
        assertEquals(endereco.getId(), resultado.get().getId(), "Os IDs devem ser iguais");
    }

    @Test
    @DisplayName("findById - deve retornar vazio quando o endereço não for encontrado")
    void testFindByIdNaoEncontrado() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Endereco> resultado = enderecoService.findById(1L);
        assertFalse(resultado.isPresent(), "O endereço não deve ser encontrado");
    }

    @Test
    @DisplayName("findById - deve retornar vazio para ID inexistente")
    void testFindByIdIdInexistente() {
        when(enderecoRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Endereco> resultado = enderecoService.findById(99L);
        assertFalse(resultado.isPresent(), "O endereço não deve ser encontrado");
    }

    @Test
    @DisplayName("save - deve salvar um endereço com sucesso")
    void testSave() {
        when(enderecoRepository.save(endereco)).thenReturn(endereco);
        Endereco resultado = enderecoService.save(endereco);
        assertEquals(endereco.getId(), resultado.getId(), "Os IDs devem ser iguais");
    }

    @Test
    @DisplayName("save - deve salvar e retornar o endereço correto")
    void testSaveRetorno() {
        when(enderecoRepository.save(endereco)).thenReturn(endereco);
        Endereco resultado = enderecoService.save(endereco);
        assertEquals("Rua A", resultado.getRua(), "A rua deve ser 'Rua A'");
        assertEquals(123, resultado.getNumero(), "O número deve ser 123");
    }

    @Test
    @DisplayName("save - deve manipular o retorno ao salvar endereço")
    void testSaveManipulacao() {
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);
        Endereco resultado = enderecoService.save(new Endereco());
        assertNotNull(resultado, "O endereço retornado não deve ser nulo");
    }

    @Test
    @DisplayName("update - deve atualizar e retornar mensagem de sucesso")
    void testUpdate() {
        when(enderecoRepository.save(endereco)).thenReturn(endereco);
        String resultado = enderecoService.update(endereco, 1L);
        assertEquals("Endereço atualizado com sucesso!!!", resultado, "A mensagem de sucesso está incorreta");
    }

    @Test
    @DisplayName("update - deve atualizar endereço existente")
    void testUpdateExistente() {
        Endereco enderecoAtualizado = new Endereco(1L, "Rua Atualizada", 321, "Bairro Atualizado", "Cidade Atualizada", "Estado Atualizado", "87654-321");
        when(enderecoRepository.save(enderecoAtualizado)).thenReturn(enderecoAtualizado);
        String resultado = enderecoService.update(enderecoAtualizado, 1L);
        assertEquals("Endereço atualizado com sucesso!!!", resultado, "A mensagem de sucesso está incorreta");
    }

    @Test
    @DisplayName("update - deve retornar mensagem de sucesso para atualização")
    void testUpdateMensagem() {
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);
        String resultado = enderecoService.update(endereco, 1L);
        assertEquals("Endereço atualizado com sucesso!!!", resultado, "A mensagem de sucesso está incorreta");
    }

    @Test
    @DisplayName("deleteById - deve deletar o endereço com sucesso")
    void testDeleteById() {
        doNothing().when(enderecoRepository).deleteById(1L);
        enderecoService.deleteById(1L);
        verify(enderecoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById - deve não fazer nada para ID inexistente")
    void testDeleteByIdInexistente() {
        doNothing().when(enderecoRepository).deleteById(99L);
        enderecoService.deleteById(99L);
        verify(enderecoRepository, times(1)).deleteById(99L);
    }

    @Test
    @DisplayName("deleteById - deve lidar com exceção ao deletar")
    void testDeleteByIdExcecao() {
        doThrow(new RuntimeException("Erro ao deletar")).when(enderecoRepository).deleteById(1L);
        assertThrows(RuntimeException.class, () -> enderecoService.deleteById(1L), "Deveria lançar uma exceção ao deletar");
    }
}
