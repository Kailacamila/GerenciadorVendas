package TestController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.LojaApplication;
import app.controller.ClienteController;
import app.entity.Cliente;
import app.service.ClienteService;
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
public class ClienteControllerTest {

    @Autowired
    private ClienteController clienteController;

    @MockBean
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setup() {
        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Kaila Camila");
        cliente.setTelefone("(45) 1234-5678");
        cliente.setCpf("123.456.789-00");
        cliente.setEmail("kaila1515@gmail.com");
        cliente.setIdade(30);

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);

        when(clienteService.save(any(Cliente.class))).thenReturn("Cliente salvo com sucesso");
        when(clienteService.update(any(Cliente.class), anyLong())).thenReturn("Cliente atualizado com sucesso");
        when(clienteService.findById(anyLong())).thenReturn(cliente);
        when(clienteService.findAll()).thenReturn(clientes);
        when(clienteService.delete(anyLong())).thenReturn("Cliente excluído com sucesso");
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Salvar cliente com sucesso")
    void saveCliente() {
        ResponseEntity<String> response = clienteController.save(cliente);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente salvo com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Atualizar cliente com sucesso")
    void updateCliente() {
        ResponseEntity<String> response = clienteController.update(cliente, cliente.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente atualizado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Buscar cliente por ID")
    void findById() {
        ResponseEntity<Cliente> response = clienteController.findById(cliente.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Listar todos os clientes")
    void findAll() {
        ResponseEntity<List<Cliente>> response = clienteController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(cliente, response.getBody().get(0));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Excluir cliente com sucesso")
    void deleteCliente() {
        ResponseEntity<String> response = clienteController.delete(cliente.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente excluído com sucesso", response.getBody());
    }


    @Test
    @DisplayName("INTEGRAÇÃO - Erro ao atualizar cliente inexistente")
    void updateCliente_NotFound() {
        when(clienteService.update(any(Cliente.class), anyLong())).thenThrow(new RuntimeException("Cliente não encontrado"));

        ResponseEntity<String> response = clienteController.update(cliente, 999L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Algo deu errado! Cliente não encontrado", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Erro ao excluir cliente inexistente")
    void deleteCliente_NotFound() {
        when(clienteService.delete(anyLong())).thenThrow(new RuntimeException("Cliente não encontrado"));

        ResponseEntity<String> response = clienteController.delete(999L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

}
