package TestService;

import app.LojaApplication;
import app.entity.Cliente;
import app.repository.ClienteRepository;
import app.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = LojaApplication.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente(1L, "João Silva", "(11) 1234-5678", "123.456.789-00", "joao.silva@example.com", 30, null, Collections.emptyList());
    }

    @Test
    public void testSaveClienteSuccess() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        String result = clienteService.save(cliente);
        assertEquals("Cliente foi cadastrado com sucesso!!!", result);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testUpdateClienteSuccess() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        String result = clienteService.update(cliente, 1L);
        assertEquals("Cliente atualizado com sucesso!!!", result);
        verify(clienteRepository, times(1)).save(cliente);
    }


    @Test
    public void testFindByIdSuccess() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        Cliente result = clienteService.findById(1L);
        assertEquals(cliente, result);
    }

    @Test
    public void testFindByIdNotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        Cliente result = clienteService.findById(1L);

        assertNull(result);
    }

    @Test
    public void testFindAllSuccess() {
        when(clienteRepository.findAll()).thenReturn(Collections.singletonList(cliente));
        List<Cliente> result = clienteService.findAll();
        assertEquals(1, result.size());
        assertEquals(cliente, result.get(0));
    }

    @Test
    public void testFindAllEmpty() {
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

        List<Cliente> result = clienteService.findAll();

        assertEquals(0, result.size());
    }
    @Test
    public void testDeleteSuccess() {
        doNothing().when(clienteRepository).deleteById(1L);
        String result = clienteService.delete(1L);
        assertEquals("Cliente deletado com sucesso", result);
        verify(clienteRepository, times(1)).deleteById(1L);
    }


}
