package app.service;

import java.util.List;
import java.util.Optional;

import app.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Produto;
import app.entity.Venda;
import app.repository.VendaRepository;
import jakarta.transaction.Transactional;

@Service
public class VendaService {
	@Autowired
	private VendaRepository vendaRepositoy;
    private Cliente cliente;
	
	@Transactional 
	public String save(Venda venda) {
		double valorTotal = 0.0;
		for(Produto produto : venda.getProdutos()) {
			valorTotal+=produto.getPreco();
		}
        if (venda.getCliente().getIdade() <18 && valorTotal >=500){
            throw new RuntimeException("Pedido recusado, por conta do cliente ser menor de 18 anos, seu limite e de R$500,00 ");
        }
		venda.setValorTotal(valorTotal);
		this.vendaRepositoy.save(venda);
		return "Venda salva com sucesso!"; 
	}
	
    public String update(Venda venda, long id) {
        venda.setId(id);
        this.vendaRepositoy.save(venda);
        return "Venda atualizada com sucesso!";
    }
    
    public Venda findById(long id) {
    	Optional<Venda> optional = this.vendaRepositoy.findById(id);
    	return optional.orElse(null); 
    }
    
    public List<Venda> findAll(){
    	return this.vendaRepositoy.findAll();
    }
    
    public String delete(long id) {
    	this.vendaRepositoy.deleteById(id);
    	return "Venda deletada com sucesso!";
    }

    public List<Venda> buscarVendasPorNomeCliente(String nomeCliente) {
    	return this.vendaRepositoy.findByClienteNomeContaining(nomeCliente);
    }

    public List<Venda> buscarVendasPorNomeFuncionario(String nomeFuncionario) {
        return this.vendaRepositoy.findByFuncionarioNomeContaining(nomeFuncionario);
    }

    public List<Venda> buscarTop10VendasPorValorTotal() {
        return this.vendaRepositoy.findTop10ByOrderByValorTotalDesc();
    }
}
