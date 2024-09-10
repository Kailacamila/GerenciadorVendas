package TestService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import app.LojaApplication;
import app.entity.Cliente;
import app.entity.Funcionario;
import app.entity.Produto;
import app.entity.Venda;
import app.repository.VendaRepository;
import app.service.VendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojaApplication.class)

class VendaServiceTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    private Venda venda;
    private Cliente cliente;
    private Funcionario funcionario;
    private Produto produto1;
    private Produto produto2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produto1 = new Produto(1L, "Produto A", "Descrição A", 100.00);
        produto2 = new Produto(2L, "Produto B", "Descrição B", 150.00);
        cliente = new Cliente(1L, "João Silva", "(11) 1234-5678", "123.456.789-00", "joao.silva@example.com", 30, null, Collections.emptyList());
        funcionario = new Funcionario(1L, "Funcionario A", "(11) 1234-5678", "email@exemplo.com", "123.456.789-00", 30, "Vendedor");
        venda = new Venda(1L, cliente, funcionario, Arrays.asList(produto1, produto2), 250.00);
    }

    // Testes para save
    @Test
    @DisplayName("save - deve salvar uma venda com sucesso")
    void testSave() {
        when(vendaRepository.save(venda)).thenReturn(venda);
        String resultado = vendaService.save(venda);
        assertEquals("Venda salva com sucesso!", resultado, "A mensagem de sucesso está incorreta");
    }

    @Test
    @DisplayName("save - deve calcular o valor total da venda")
    void testSaveCalculaValorTotal() {
        when(vendaRepository.save(venda)).thenReturn(venda);
        vendaService.save(venda);
        assertEquals(250.00, venda.getValorTotal(), "O valor total está incorreto");
    }



    @Test
    @DisplayName("update - deve atualizar uma venda com sucesso")
    void testUpdate() {
        when(vendaRepository.save(venda)).thenReturn(venda);
        String resultado = vendaService.update(venda, 1L);
        assertEquals("Venda atualizada com sucesso!", resultado, "A mensagem de sucesso está incorreta");
    }

    @Test
    @DisplayName("findById - deve retornar uma venda quando encontrada")
    void testFindById() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));
        Venda resultado = vendaService.findById(1L);
        assertNotNull(resultado, "A venda deve ser encontrada");
        assertEquals(venda.getId(), resultado.getId(), "Os IDs devem ser iguais");
    }

    @Test
    @DisplayName("findById - deve retornar nulo quando a venda não for encontrada")
    void testFindByIdNaoEncontrado() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.empty());
        Venda resultado = vendaService.findById(1L);
        assertNull(resultado, "A venda não deve ser encontrada");
    }

    @Test
    @DisplayName("findAll - deve retornar lista de vendas")
    void testFindAll() {
        when(vendaRepository.findAll()).thenReturn(Arrays.asList(venda));
        List<Venda> resultado = vendaService.findAll();
        assertEquals(1, resultado.size(), "A lista de vendas deve ter 1 elemento");
    }

    @Test
    @DisplayName("findAll - deve retornar lista vazia quando nenhuma venda estiver presente")
    void testFindAllVazio() {
        when(vendaRepository.findAll()).thenReturn(Arrays.asList());
        List<Venda> resultado = vendaService.findAll();
        assertTrue(resultado.isEmpty(), "A lista de vendas deve estar vazia");
    }

    @Test
    @DisplayName("delete - deve deletar uma venda com sucesso")
    void testDelete() {
        doNothing().when(vendaRepository).deleteById(1L);
        String resultado = vendaService.delete(1L);
        assertEquals("Venda deletada com sucesso!", resultado, "A mensagem de sucesso está incorreta");
    }

    @Test
    @DisplayName("delete - deve não fazer nada para ID inexistente")
    void testDeleteInexistente() {
        doNothing().when(vendaRepository).deleteById(99L);
        String resultado = vendaService.delete(99L);
        assertEquals("Venda deletada com sucesso!", resultado, "A mensagem de sucesso está incorreta");
    }

    @Test
    @DisplayName("buscarVendasPorNomeCliente - deve retornar vendas com base no nome do cliente")
    void testBuscarVendasPorNomeCliente() {
        when(vendaRepository.findByClienteNomeContaining("Cliente A")).thenReturn(Arrays.asList(venda));
        List<Venda> resultado = vendaService.buscarVendasPorNomeCliente("Cliente A");
        assertEquals(1, resultado.size(), "A lista de vendas deve ter 1 elemento");
        assertEquals(cliente.getNome(), resultado.get(0).getCliente().getNome(), "Os nomes dos clientes devem ser iguais");
    }

    @Test
    @DisplayName("buscarVendasPorNomeCliente - deve retornar lista vazia quando nenhum cliente corresponder")
    void testBuscarVendasPorNomeClienteVazio() {
        when(vendaRepository.findByClienteNomeContaining("Cliente X")).thenReturn(Arrays.asList());
        List<Venda> resultado = vendaService.buscarVendasPorNomeCliente("Cliente X");
        assertTrue(resultado.isEmpty(), "A lista de vendas deve estar vazia");
    }

    @Test
    @DisplayName("buscarVendasPorNomeFuncionario - deve retornar vendas com base no nome do funcionário")
    void testBuscarVendasPorNomeFuncionario() {
        when(vendaRepository.findByFuncionarioNomeContaining("Funcionario A")).thenReturn(Arrays.asList(venda));
        List<Venda> resultado = vendaService.buscarVendasPorNomeFuncionario("Funcionario A");
        assertEquals(1, resultado.size(), "A lista de vendas deve ter 1 elemento");
        assertEquals(funcionario.getNome(), resultado.get(0).getFuncionario().getNome(), "Os nomes dos funcionários devem ser iguais");
    }

    @Test
    @DisplayName("buscarVendasPorNomeFuncionario - deve retornar lista vazia quando nenhum funcionário corresponder")
    void testBuscarVendasPorNomeFuncionarioVazio() {
        when(vendaRepository.findByFuncionarioNomeContaining("Funcionario X")).thenReturn(Arrays.asList());
        List<Venda> resultado = vendaService.buscarVendasPorNomeFuncionario("Funcionario X");
        assertTrue(resultado.isEmpty(), "A lista de vendas deve estar vazia");
    }

    // Testes para buscarTop10VendasPorValorTotal
    @Test
    @DisplayName("buscarTop10VendasPorValorTotal - deve retornar as 10 maiores vendas")
    void testBuscarTop10VendasPorValorTotal() {
        Venda venda2 = new Venda(2L, cliente, funcionario, Arrays.asList(produto1), 150.00);
        when(vendaRepository.findTop10ByOrderByValorTotalDesc()).thenReturn(Arrays.asList(venda, venda2));
        List<Venda> resultado = vendaService.buscarTop10VendasPorValorTotal();
        assertEquals(2, resultado.size(), "A lista de vendas deve ter 2 elementos");
        assertTrue(resultado.get(0).getValorTotal() >= resultado.get(1).getValorTotal(), "As vendas não estão ordenadas corretamente");
    }
}
