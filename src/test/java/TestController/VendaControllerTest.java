package TestController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import app.LojaApplication;
import app.controller.VendaController;
import app.entity.Cliente;
import app.entity.Funcionario;
import app.entity.Produto;
import app.entity.Venda;
import app.service.VendaService;
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
public class VendaControllerTest {

    @Autowired
    private VendaController vendaController;

    @MockBean
    private VendaService vendaService;

    private Venda venda;
    private Cliente cliente;
    private Funcionario funcionario;
    private Produto produto;

    @BeforeEach
    void setup() {
        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Cliente Teste");

        funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Funcionario Teste");

        produto = new Produto();
        produto.setId(1);
        produto.setNome("Produto Teste");
        produto.setPreco(50.0);

        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);

        venda = new Venda();
        venda.setId(1);
        venda.setCliente(cliente);
        venda.setFuncionario(funcionario);
        venda.setProdutos(produtos);
        venda.setValorTotal(50.0);

        List<Venda> vendas = new ArrayList<>();
        vendas.add(venda);

        when(vendaService.save(any(Venda.class))).thenReturn("Venda salva com sucesso");
        when(vendaService.update(any(Venda.class), anyLong())).thenReturn("Venda atualizada com sucesso");
        when(vendaService.findById(anyLong())).thenReturn(venda);
        when(vendaService.findAll()).thenReturn(vendas);
        when(vendaService.delete(anyLong())).thenReturn("Venda excluída com sucesso");
        when(vendaService.buscarVendasPorNomeCliente(anyString())).thenReturn(vendas);
        when(vendaService.buscarVendasPorNomeFuncionario(anyString())).thenReturn(vendas);
        when(vendaService.buscarTop10VendasPorValorTotal()).thenReturn(vendas);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Salvar venda com sucesso")
    void saveVenda() {
        ResponseEntity<String> response = vendaController.save(venda);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Venda salva com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Atualizar venda com sucesso")
    void updateVenda() {
        ResponseEntity<String> response = vendaController.update(venda, venda.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Venda atualizada com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Buscar venda por ID")
    void findById() {
        ResponseEntity<Venda> response = vendaController.findById(venda.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(venda, response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Listar todas as vendas")
    void findAll() {
        ResponseEntity<List<Venda>> response = vendaController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(venda, response.getBody().get(0));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Excluir venda com sucesso")
    void deleteVenda() {
        ResponseEntity<String> response = vendaController.delete(venda.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Venda excluída com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Buscar vendas por nome do cliente")
    void buscarVendasPorNomeCliente() {
        ResponseEntity<List<Venda>> response = vendaController.buscarVendasPorNomeCliente("Cliente Teste");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(venda, response.getBody().get(0));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Buscar vendas por nome do funcionário")
    void buscarVendasPorNomeFuncionario() {
        ResponseEntity<List<Venda>> response = vendaController.buscarVendasPorNomeFuncionario("Funcionario Teste");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(venda, response.getBody().get(0));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Buscar top 10 vendas por valor total")
    void buscarTop10VendasPorValorTotal() {
        ResponseEntity<List<Venda>> response = vendaController.buscarTop10VendasPorValorTotal();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(venda, response.getBody().get(0));
    }
    @Test
    @DisplayName("INTEGRAÇÃO - Erro ao atualizar venda não encontrada")
    void updateVendaNotFound() {
        when(vendaService.update(any(Venda.class), anyLong())).thenThrow(new RuntimeException("Venda não encontrada"));
        ResponseEntity<String> response = vendaController.update(venda, venda.getId());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Algo deu errado! Venda não encontrada", response.getBody());
    }
    @Test
    @DisplayName("INTEGRAÇÃO - Erro ao buscar venda por ID não encontrada")
    void findByIdNotFound() {
        when(vendaService.findById(anyLong())).thenReturn(null);
        ResponseEntity<Venda> response = vendaController.findById(99L);  // ID inexistente
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @DisplayName("INTEGRAÇÃO - Erro ao excluir venda inexistente")
    void deleteVendaNotFound() {
        when(vendaService.delete(anyLong())).thenThrow(new RuntimeException("Venda não encontrada"));
        ResponseEntity<String> response = vendaController.delete(99L);  // ID inexistente
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    @DisplayName("INTEGRAÇÃO - Erro ao buscar vendas por nome de cliente não encontrado")
    void buscarVendasPorNomeClienteNotFound() {
        when(vendaService.buscarVendasPorNomeCliente(anyString())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Venda>> response = vendaController.buscarVendasPorNomeCliente("Cliente Inexistente");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
    @Test
    @DisplayName("INTEGRAÇÃO - Erro ao buscar vendas por nome de funcionário não encontrado")
    void buscarVendasPorNomeFuncionarioNotFound() {
        when(vendaService.buscarVendasPorNomeFuncionario(anyString())).thenReturn(new ArrayList<>());  // Nenhuma venda encontrada

        ResponseEntity<List<Venda>> response = vendaController.buscarVendasPorNomeFuncionario("Funcionario Inexistente");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Erro ao buscar top 10 vendas por valor total")
    void buscarTop10VendasPorValorTotalWithError() {
        when(vendaService.buscarTop10VendasPorValorTotal()).thenThrow(new RuntimeException("Erro ao buscar top 10 vendas"));
        ResponseEntity<List<Venda>> response = vendaController.buscarTop10VendasPorValorTotal();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

}
