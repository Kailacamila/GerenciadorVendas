package TestController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import app.LojaApplication;
import app.controller.ProdutoController;
import app.entity.Produto;
import app.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = LojaApplication.class)
public class ProdutoControllerTest {

    @Autowired
    private ProdutoController produtoController;

    @MockBean
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    void setup() {
        produto = new Produto();
        produto.setId(1);
        produto.setNome("Camisa Branca ");
        produto.setDescricao("Tecido de algodão");
        produto.setPreco(49.90);

        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);

        when(produtoService.save(any(Produto.class))).thenReturn("Produto salvo com sucesso");
        when(produtoService.update(any(Produto.class), anyLong())).thenReturn("Produto atualizado com sucesso");
        when(produtoService.findById(anyLong())).thenReturn(produto);
        when(produtoService.findAll()).thenReturn(produtos);
        when(produtoService.delete(anyLong())).thenReturn("Produto excluído com sucesso");
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Salvar produto com sucesso")
    void saveProduto() {
        ResponseEntity<String> response = produtoController.save(produto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto salvo com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Atualizar produto com sucesso")
    void updateProduto() {
        ResponseEntity<String> response = produtoController.update(produto, produto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto atualizado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Buscar produto por ID")
    void findById() {
        ResponseEntity<Produto> response = produtoController.findById(produto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produto, response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Listar todos os produtos")
    void findAll() {
        ResponseEntity<List<Produto>> response = produtoController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(produto, response.getBody().get(0));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Excluir produto com sucesso")
    void deleteProduto() {
        ResponseEntity<String> response = produtoController.delete(produto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto excluído com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Atualizar produto com ID inexistente")
    void updateProdutoNotFound() {
        Produto produtoUpdate = new Produto();
        produtoUpdate.setNome("Camisa Azul");
        produtoUpdate.setDescricao("Tecido de algodão");
        produtoUpdate.setPreco(59.90);

        when(produtoService.update(any(Produto.class), anyLong())).thenThrow(new RuntimeException("Produto não encontrado"));

        ResponseEntity<String> response = produtoController.update(produtoUpdate, 999L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Algo deu errado! Produto não encontrado", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Excluir produto com ID inexistente")
    void deleteProdutoNotFound() {
        when(produtoService.delete(anyLong())).thenThrow(new RuntimeException("Produto não encontrado"));

        ResponseEntity<String> response = produtoController.delete(9999L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }


}
