package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Venda;
import app.entity.Venda;
import app.service.VendaService;

@RestController
@RequestMapping("api/venda")
public class VendaController {
	@Autowired
	private VendaService vendaService;
	
	@PostMapping("")
	public ResponseEntity<String> save(@RequestBody Venda venda){
		try {
			String mensagem = this.vendaService.save(venda);
			return new ResponseEntity<>(mensagem,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("Algo deu errado! " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<String> update(@RequestBody Venda venda, @PathVariable long id){
		try {
			String mensagem = this.vendaService.update(venda, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("Algo deu errado!"+e.getMessage(),HttpStatus.BAD_REQUEST);
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
    public List<Venda> buscarVendasPorNomeCliente(@PathVariable String nomeCliente) {
        return vendaService.buscarVendasPorNomeCliente(nomeCliente);
    }

    @GetMapping("/funcionario/{nomeFuncionario}")
    public List<Venda> buscarVendasPorNomeFuncionario(@PathVariable String nomeFuncionario) {
        return vendaService.buscarVendasPorNomeFuncionario(nomeFuncionario);
    }

    @GetMapping("/top10")
    public List<Venda> buscarTop10VendasPorValorTotal() {
        return vendaService.buscarTop10VendasPorValorTotal();
    }
}
