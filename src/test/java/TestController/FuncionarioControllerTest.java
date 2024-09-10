package TestController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import app.LojaApplication;
import app.controller.FuncionarioController;
import app.entity.Funcionario;
import app.service.FuncionarioService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = LojaApplication.class)
public class FuncionarioControllerTest {

    @Autowired
    private FuncionarioController funcionarioController;

    @MockBean
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;

    @BeforeEach
    void setup() {
        funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Andrei Back");
        funcionario.setTelefone("(45) 9876-5432");
        funcionario.setEmail("andreiback@gmail.com");
        funcionario.setCpf("987.654.321-00");
        funcionario.setIdade(28);
        funcionario.setFuncao("Gerente");

        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(funcionario);

        when(funcionarioService.save(any(Funcionario.class))).thenReturn("Funcionário salvo com sucesso");
        when(funcionarioService.update(any(Funcionario.class), anyLong())).thenReturn("Funcionário atualizado com sucesso");
        when(funcionarioService.findById(anyLong())).thenReturn(funcionario);
        when(funcionarioService.findAll()).thenReturn(funcionarios);
        when(funcionarioService.delete(anyLong())).thenReturn("Funcionário excluído com sucesso");
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Salvar funcionário com sucesso")
    void saveFuncionario() {
        ResponseEntity<String> response = funcionarioController.save(funcionario);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário salvo com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Atualizar funcionário com sucesso")
    void updateFuncionario() {
        ResponseEntity<String> response = funcionarioController.update(funcionario, funcionario.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário atualizado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Buscar funcionário por ID")
    void findById() {
        ResponseEntity<Funcionario> response = funcionarioController.findById(funcionario.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(funcionario, response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Listar todos os funcionários")
    void findAll() {
        ResponseEntity<List<Funcionario>> response = funcionarioController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(funcionario, response.getBody().get(0));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Excluir funcionário com sucesso")
    void deleteFuncionario() {
        ResponseEntity<String> response = funcionarioController.delete(funcionario.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário excluído com sucesso", response.getBody());
    }
    @Test
    @DisplayName("INTEGRAÇÃO - Buscar funcionário inexistente por ID")
    void findByIdNotFound() {
        when(funcionarioService.findById(anyLong())).thenReturn(null);
        ResponseEntity<Funcionario> response = funcionarioController.findById(999L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    @DisplayName("INTEGRAÇÃO - Atualizar funcionário inexistente")
    void updateFuncionarioNotFound() {
        when(funcionarioService.update(any(Funcionario.class), anyLong())).thenReturn("Funcionário não encontrado");
        ResponseEntity<String> response = funcionarioController.update(funcionario, 999L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Excluir funcionário inexistente")
    void deleteFuncionarioNotFound() {
        when(funcionarioService.delete(anyLong())).thenReturn("Funcionário não encontrado");
        ResponseEntity<String> response = funcionarioController.delete(999L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário não encontrado", response.getBody());
    }
    @Test
    @DisplayName("VALIDAÇÃO - Deve lançar exceção ao salvar funcionário com nome inválido")
    void saveFuncionarioNomeInvalido() {
        funcionario.setNome("Andrei");
        assertThrows(ConstraintViolationException.class, () -> funcionarioController.save(funcionario));
    }

    @Test
    @DisplayName("VALIDAÇÃO - Deve lançar exceção ao salvar funcionário com telefone inválido")
    void saveFuncionarioTelefoneInvalido() {
        funcionario.setTelefone("12345");
        assertThrows(ConstraintViolationException.class, () -> funcionarioController.save(funcionario));
    }

    @Test
    @DisplayName("VALIDAÇÃO - Deve lançar exceção ao salvar funcionário com e-mail inválido")
    void saveFuncionarioEmailInvalido() {
        funcionario.setEmail("email_invalido");
        assertThrows(ConstraintViolationException.class, () -> funcionarioController.save(funcionario));
    }

    @Test
    @DisplayName("VALIDAÇÃO - Deve lançar exceção ao salvar funcionário com CPF inválido")
    void saveFuncionarioCpfInvalido() {
        funcionario.setCpf("123.456.789-00");
        assertThrows(ConstraintViolationException.class, () -> funcionarioController.save(funcionario));
    }

    @Test
    @DisplayName("VALIDAÇÃO - Deve lançar exceção ao salvar funcionário com idade inválida")
    void saveFuncionarioIdadeInvalida() {
        funcionario.setIdade(150);
        assertThrows(ConstraintViolationException.class, () -> funcionarioController.save(funcionario));
    }

    @Test
    @DisplayName("VALIDAÇÃO - Deve lançar exceção ao salvar funcionário com função vazia")
    void saveFuncionarioFuncaoVazia() {
        funcionario.setFuncao("");  // Função vazia
        assertThrows(ConstraintViolationException.class, () -> funcionarioController.save(funcionario));
    }
    @Test
    @DisplayName("INTEGRAÇÃO - Listar todos os funcionários com campos válidos")
    void findAllFuncionariosValid() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(funcionario);

        when(funcionarioService.findAll()).thenReturn(funcionarios);

        ResponseEntity<List<Funcionario>> response = funcionarioController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(funcionario, response.getBody().get(0));
    }
    @Test
    @DisplayName("INTEGRAÇÃO - Excluir funcionário com ID válido")
    void deleteFuncionarioValid() {
        ResponseEntity<String> response = funcionarioController.delete(funcionario.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário excluído com sucesso", response.getBody());
    }
    @Test
    @DisplayName("INTEGRAÇÃO - Excluir funcionário com ID inválido")
    void deleteFuncionarioWithInvalidId() {
        doThrow(new RuntimeException("Funcionário não encontrado")).when(funcionarioService).delete(anyLong());
        ResponseEntity<String> response = funcionarioController.delete(999L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
    @Test
    @DisplayName("INTEGRAÇÃO - Atualizar funcionário com ID inválido")
    void updateFuncionarioWithInvalidId() {
        when(funcionarioService.findById(anyLong())).thenReturn(null);
        when(funcionarioService.update(any(Funcionario.class), anyLong())).thenThrow(new RuntimeException("Funcionário não encontrado"));

        ResponseEntity<String> response = funcionarioController.update(funcionario, 999L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Algo deu errado! Funcionário não encontrado", response.getBody());
    }

}

