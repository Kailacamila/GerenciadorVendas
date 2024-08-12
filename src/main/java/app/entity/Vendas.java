package app.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
	private Double vlTotal;
	
	@ManyToOne
	@JoinColumn(name="funcionarios_id")
	@JsonIgnoreProperties("vendas")
	private Funcionarios funcionarios;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Clientes clientes;
	
	@ManyToMany
	@JoinTable(name="venda_tem_produto")
	private List<Produto> produtos;

}
