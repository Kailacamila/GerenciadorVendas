package app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private long id;
	@NotNull (message = "O campo do nome não pode ser vazio")
	private String nome;
	@NotNull  (message = "o campo de e-mail não pode estar vazio")
	@Email    (message = "tem que estar no formato de e-mail")
	private String email;

	private int idade;
	private String cep;
	
	@OneToMany(mappedBy = "cliente")
	@JsonIgnoreProperties("cliente")
	private List<Venda> vendas;
	
}
