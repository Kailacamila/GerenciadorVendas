package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Funcionarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	private String nome;
	private String email;
	private String telefone;
	private int idade;
	private String  endereco;
	private String funcao;
	
	
	@OneToMany(mappedBy = "funcionario")
	@JsonIgnoreProperties("funcionario")
	private List<Vendas> vendas;
}