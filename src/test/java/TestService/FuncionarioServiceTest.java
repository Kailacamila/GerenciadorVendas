package TestService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import app.LojaApplication;
import app.entity.Funcionario;
import app.repository.FuncionarioRepository;
import app.service.FuncionarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojaApplication.class)
class FuncionarioServiceTest {

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        funcionario = new Funcionario(1L, "Kaila", "(12) 3456-7890", "kaila.camila@example.com", "123.456.789-00", 30, "Gerente");
    }

    @Test
    @DisplayName("save - deve salvar um funcionário com sucesso")
    void testSave() {
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);
        String resultado = funcionarioService.save(funcionario);
        assertEquals("Funcionario foi cadastrado com sucesso!", resultado, "A mensagem de sucesso está incorreta");
    }


    @Test
    @DisplayName("save - deve lidar com dados inválidos ao salvar")
    void testSaveDadosInvalidos() {
        Funcionario funcionarioInvalido = new Funcionario();
        when(funcionarioRepository.save(funcionarioInvalido)).thenThrow(new RuntimeException("Erro ao salvar"));
        Exception exception = assertThrows(RuntimeException.class, () -> funcionarioService.save(funcionarioInvalido));
        assertEquals("Erro ao salvar", exception.getMessage());
    }

    @Test
    @DisplayName("update - deve atualizar um funcionário com sucesso")
    void testUpdate() {
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);
        String resultado = funcionarioService.update(funcionario, 1L);
        assertEquals("Funcionario atualizado com sucesso!", resultado, "A mensagem de sucesso está incorreta");
    }



    @Test
    @DisplayName("update - deve lidar com dados inválidos ao atualizar")
    void testUpdateDadosInvalidos() {
        Funcionario funcionarioInvalido = new Funcionario();
        when(funcionarioRepository.save(funcionarioInvalido)).thenThrow(new RuntimeException("Erro ao atualizar"));
        Exception exception = assertThrows(RuntimeException.class, () -> funcionarioService.update(funcionarioInvalido, 1L));
        assertEquals("Erro ao atualizar", exception.getMessage());
    }

    @Test
    @DisplayName("findById - deve retornar um funcionário quando encontrado")
    void testFindById() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        Funcionario resultado = funcionarioService.findById(1L);
        assertNotNull(resultado, "O funcionário deve ser encontrado");
        assertEquals(funcionario.getId(), resultado.getId(), "Os IDs devem ser iguais");
    }

    @Test
    @DisplayName("findById - deve retornar nulo quando o funcionário não for encontrado")
    void testFindByIdNaoEncontrado() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());
        Funcionario resultado = funcionarioService.findById(1L);
        assertNull(resultado, "O funcionário não deve ser encontrado");
    }

    @Test
    @DisplayName("findById - deve lidar com exceção ao buscar funcionário")
    void testFindByIdExcecao() {
        when(funcionarioRepository.findById(1L)).thenThrow(new RuntimeException("Erro ao buscar"));
        Exception exception = assertThrows(RuntimeException.class, () -> funcionarioService.findById(1L));
        assertEquals("Erro ao buscar", exception.getMessage());
    }

    @Test
    @DisplayName("findAll - deve retornar lista de funcionários")
    void testFindAll() {
        when(funcionarioRepository.findAll()).thenReturn(Arrays.asList(funcionario));
        List<Funcionario> resultado = funcionarioService.findAll();
        assertEquals(1, resultado.size(), "A lista de funcionários deve ter 1 elemento");
    }

    @Test
    @DisplayName("findAll - deve retornar lista vazia quando nenhum funcionário estiver presente")
    void testFindAllVazio() {
        when(funcionarioRepository.findAll()).thenReturn(Arrays.asList());
        List<Funcionario> resultado = funcionarioService.findAll();
        assertTrue(resultado.isEmpty(), "A lista de funcionários deve estar vazia");
    }

    @Test
    @DisplayName("findAll - deve retornar lista com múltiplos funcionários")
    void testFindAllMultiplo() {
        Funcionario funcionario2 = new Funcionario(2L, "Ana Costa", "(31) 1234-5678", "ana.costa@example.com", "111.222.333-44", 29, "Analista");
        when(funcionarioRepository.findAll()).thenReturn(Arrays.asList(funcionario, funcionario2));
        List<Funcionario> resultado = funcionarioService.findAll();
        assertEquals(2, resultado.size(), "A lista de funcionários deve ter 2 elementos");
    }

    @Test
    @DisplayName("delete - deve deletar o funcionário com sucesso")
    void testDelete() {
        doNothing().when(funcionarioRepository).deleteById(1L);
        String resultado = funcionarioService.delete(1L);
        assertEquals("Funcionario deletado com sucesso", resultado, "A mensagem de sucesso está incorreta");
    }

    @Test
    @DisplayName("delete - deve não fazer nada para ID inexistente")
    void testDeleteInexistente() {
        doNothing().when(funcionarioRepository).deleteById(99L);
        String resultado = funcionarioService.delete(99L);
        assertEquals("Funcionario deletado com sucesso", resultado, "A mensagem de sucesso está incorreta");
    }

    @Test
    @DisplayName("delete - deve lidar com exceção ao deletar")
    void testDeleteExcecao() {
        doThrow(new RuntimeException("Erro ao deletar")).when(funcionarioRepository).deleteById(1L);
        Exception exception = assertThrows(RuntimeException.class, () -> funcionarioService.delete(1L));
        assertEquals("Erro ao deletar", exception.getMessage());
    }
}
