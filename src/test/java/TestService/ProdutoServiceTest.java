package TestService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import app.LojaApplication;
import app.entity.Produto;
import app.repository.ProdutoRepository;
import app.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojaApplication.class)
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private Produto produto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produto = new Produto(1L, "Produto A", "Descrição do Produto A", 99.99);
    }

    @Test
    @DisplayName("save - deve salvar um produto com sucesso")
    void testSave() {
        when(produtoRepository.save(produto)).thenReturn(produto);
        String resultado = produtoService.save(produto);
        assertEquals("Produto salvo com sucesso!", resultado, "A mensagem de sucesso está incorreta");
    }


    @Test
    @DisplayName("save - deve lidar com dados inválidos ao salvar")
    void testSaveDadosInvalidos() {
        Produto produtoInvalido = new Produto();
        when(produtoRepository.save(produtoInvalido)).thenThrow(new RuntimeException("Erro ao salvar"));
        Exception exception = assertThrows(RuntimeException.class, () -> produtoService.save(produtoInvalido));
        assertEquals("Erro ao salvar", exception.getMessage());
    }

    @Test
    @DisplayName("update - deve atualizar um produto com sucesso")
    void testUpdate() {
        when(produtoRepository.save(produto)).thenReturn(produto);
        String resultado = produtoService.update(produto, 1L);
        assertEquals("Produto atualizado com sucesso!", resultado, "A mensagem de sucesso está incorreta");
    }


    @Test
    @DisplayName("update - deve lidar com dados inválidos ao atualizar")
    void testUpdateDadosInvalidos() {
        Produto produtoInvalido = new Produto();
        when(produtoRepository.save(produtoInvalido)).thenThrow(new RuntimeException("Erro ao atualizar"));
        Exception exception = assertThrows(RuntimeException.class, () -> produtoService.update(produtoInvalido, 1L));
        assertEquals("Erro ao atualizar", exception.getMessage());
    }

    @Test
    @DisplayName("findById - deve retornar um produto quando encontrado")
    void testFindById() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        Produto resultado = produtoService.findById(1L);
        assertNotNull(resultado, "O produto deve ser encontrado");
        assertEquals(produto.getId(), resultado.getId(), "Os IDs devem ser iguais");
    }

    @Test
    @DisplayName("findById - deve retornar nulo quando o produto não for encontrado")
    void testFindByIdNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());
        Produto resultado = produtoService.findById(1L);
        assertNull(resultado, "O produto não deve ser encontrado");
    }

    @Test
    @DisplayName("findById - deve lidar com exceção ao buscar produto")
    void testFindByIdExcecao() {
        when(produtoRepository.findById(1L)).thenThrow(new RuntimeException("Erro ao buscar"));
        Exception exception = assertThrows(RuntimeException.class, () -> produtoService.findById(1L));
        assertEquals("Erro ao buscar", exception.getMessage());
    }

    @Test
    @DisplayName("findAll - deve retornar lista de produtos")
    void testFindAll() {
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto));
        List<Produto> resultado = produtoService.findAll();
        assertEquals(1, resultado.size(), "A lista de produtos deve ter 1 elemento");
    }

    @Test
    @DisplayName("findAll - deve retornar lista vazia quando nenhum produto estiver presente")
    void testFindAllVazio() {
        when(produtoRepository.findAll()).thenReturn(Arrays.asList());
        List<Produto> resultado = produtoService.findAll();
        assertTrue(resultado.isEmpty(), "A lista de produtos deve estar vazia");
    }

    @Test
    @DisplayName("findAll - deve retornar lista com múltiplos produtos")
    void testFindAllMultiplo() {
        Produto produto2 = new Produto(2L, "Produto C", "Descrição do Produto C", 89.99);
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto, produto2));
        List<Produto> resultado = produtoService.findAll();
        assertEquals(2, resultado.size(), "A lista de produtos deve ter 2 elementos");
    }

    // Testes para delete
    @Test
    @DisplayName("delete - deve deletar o produto com sucesso")
    void testDelete() {
        doNothing().when(produtoRepository).deleteById(1L);
        String resultado = produtoService.delete(1L);
        assertEquals("Produto deletado com sucesso!", resultado, "A mensagem de sucesso está incorreta");
    }

    @Test
    @DisplayName("delete - deve não fazer nada para ID inexistente")
    void testDeleteInexistente() {
        doNothing().when(produtoRepository).deleteById(99L);
        String resultado = produtoService.delete(99L);
        assertEquals("Produto deletado com sucesso!", resultado, "A mensagem de sucesso está incorreta");
    }

    @Test
    @DisplayName("delete - deve lidar com exceção ao deletar")
    void testDeleteExcecao() {
        doThrow(new RuntimeException("Erro ao deletar")).when(produtoRepository).deleteById(1L);
        Exception exception = assertThrows(RuntimeException.class, () -> produtoService.delete(1L));
        assertEquals("Erro ao deletar", exception.getMessage());
    }
}
