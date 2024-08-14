package app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JsonIgnoreProperties("vendas")
	@NotNull (message = "A venda não pode ser realizado sem o cliente !!")
	private Cliente cliente;
	@ManyToOne
	@JsonIgnoreProperties("vendas")
	private Funcionario funcionario;
	@ManyToMany
	@JoinTable(name="venda_tem_produto")
	private List<Produto> produtos;
	
	private double valorTotal;

	public void setValorTotal(double valorTotal2) {


		
	}

}
