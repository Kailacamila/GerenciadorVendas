package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import app.entity.Venda;
import app.service.VendaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/venda")
@Validated  // Anotação para validar as requisições
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping("")
    public ResponseEntity<String> save(@Valid @RequestBody Venda venda) {
        try {
            String mensagem = this.vendaService.save(venda);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody Venda venda, @PathVariable long id) {
        try {
            String mensagem = this.vendaService.update(venda, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> findById(@PathVariable long id) {
        try {
            Venda venda = this.vendaService.findById(id);
            return new ResponseEntity<>(venda, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Venda>> findAll() {
        try {
            List<Venda> lista = this.vendaService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            String mensagem = this.vendaService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cliente/{nomeCliente}")
    public ResponseEntity<List<Venda>> buscarVendasPorNomeCliente(@PathVariable String nomeCliente) {
        try {
            List<Venda> vendas = vendaService.buscarVendasPorNomeCliente(nomeCliente);
            return new ResponseEntity<>(vendas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/funcionario/{nomeFuncionario}")
    public ResponseEntity<List<Venda>> buscarVendasPorNomeFuncionario(@PathVariable String nomeFuncionario) {
        try {
            List<Venda> vendas = vendaService.buscarVendasPorNomeFuncionario(nomeFuncionario);
            return new ResponseEntity<>(vendas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/top10")
    public ResponseEntity<List<Venda>> buscarTop10VendasPorValorTotal() {
        try {
            List<Venda> vendas = vendaService.buscarTop10VendasPorValorTotal();
            return new ResponseEntity<>(vendas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
