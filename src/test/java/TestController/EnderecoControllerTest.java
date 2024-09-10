package TestController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import app.LojaApplication;
import app.controller.EnderecoController;
import app.entity.Endereco;
import app.service.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = LojaApplication.class)
public class EnderecoControllerTest {

    @Autowired
    private EnderecoController enderecoController;

    @MockBean
    private EnderecoService enderecoService;

    private Endereco endereco;

    @BeforeEach
    void setup() {
        endereco = new Endereco(1L, "Rua rural", 123, "Bairro bacana", "Cidade Cinder", "Estado Estrondoso", "12345-678");

        when(enderecoService.save(any(Endereco.class))).thenReturn(endereco);
        when(enderecoService.findById(anyLong())).thenReturn(Optional.of(endereco));
        when(enderecoService.findAll()).thenReturn(Arrays.asList(endereco));
        when(enderecoService.update(any(Endereco.class), anyLong())).thenReturn("Endereço atualizado com sucesso");
        doNothing().when(enderecoService).deleteById(anyLong());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Buscar todos os endereços")
    void getAllEnderecos() {
        ResponseEntity<List<Endereco>> response = enderecoController.getAllEnderecos();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(endereco, response.getBody().get(0));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Buscar endereço por ID")
    void getEnderecoById() {
        ResponseEntity<Endereco> response = enderecoController.getEnderecoById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(endereco, response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Criar novo endereço")
    void createEndereco() {
        ResponseEntity<Endereco> response = enderecoController.createEndereco(endereco);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(endereco, response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Atualizar endereço")
    void updateEndereco() {
        ResponseEntity<String> response = enderecoController.update(endereco, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Endereço atualizado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Excluir endereço por ID")
    void deleteEndereco() {
        ResponseEntity<Void> response = enderecoController.deleteEndereco(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Endereço não encontrado ao tentar excluir")
    void deleteEnderecoNotFound() {
        when(enderecoService.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<Void> response = enderecoController.deleteEndereco(2L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
