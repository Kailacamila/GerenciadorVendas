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
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotNull (message = "o campo não pode estar vazio")
	@Pattern(regexp = "^(\\w+\\s+\\w+).*$", message = "O nome deve conter pelo menos duas palavras separadas por um espaço.")
	private String nome;
	@NotNull (message =  "O campo de e-mail não pode estar vazio")
	@Email (message = "Precisa estar no formato de e-mail")
	private String Telefone;
	@Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$")
	private String email;
	private String CPF;
	@org.hibernate.validator.constraints.br.CPF // VERIFICAR O PQ APARECE ASSIM //
    @Max(130)
	@NotNull (message = "O campo não pode estar vazio")
	private String idade;
    @NotBlank (message = "A função não pode estar vazia")
	private String funcao;
	@Pattern(regexp = "\"\\\\d{5}-\\\\d{3}\", message = \"O CEP deve estar no formato 12345-678.\""  )
	private String cep;
	
	@OneToMany(mappedBy = "funcionario")
	@JsonIgnoreProperties("funcionario")
	private List<Venda> vendas;
}
