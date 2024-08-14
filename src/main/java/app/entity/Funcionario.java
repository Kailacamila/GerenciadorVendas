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
import org.hibernate.validator.constraints.br.CPF;

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
	@CPF
	private String CPF;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public @NotNull(message = "o campo não pode estar vazio") @Pattern(regexp = "^(\\w+\\s+\\w+).*$", message = "O nome deve conter pelo menos duas palavras separadas por um espaço.") String getNome() {
		return nome;
	}

	public void setNome(@NotNull(message = "o campo não pode estar vazio") @Pattern(regexp = "^(\\w+\\s+\\w+).*$", message = "O nome deve conter pelo menos duas palavras separadas por um espaço.") String nome) {
		this.nome = nome;
	}

	public @NotNull(message = "O campo de e-mail não pode estar vazio") @Email(message = "Precisa estar no formato de e-mail") String getTelefone() {
		return Telefone;
	}

	public void setTelefone(@NotNull(message = "O campo de e-mail não pode estar vazio") @Email(message = "Precisa estar no formato de e-mail") String telefone) {
		Telefone = telefone;
	}

	public @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$") String getEmail() {
		return email;
	}

	public void setEmail(@Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$") String email) {
		this.email = email;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public @org.hibernate.validator.constraints.br.CPF @Max(130) @NotNull(message = "O campo não pode estar vazio") String getIdade() {
		return idade;
	}

	public void setIdade(@org.hibernate.validator.constraints.br.CPF @Max(130) @NotNull(message = "O campo não pode estar vazio") String idade) {
		this.idade = idade;
	}

	public @NotBlank(message = "A função não pode estar vazia") String getFuncao() {
		return funcao;
	}

	public void setFuncao(@NotBlank(message = "A função não pode estar vazia") String funcao) {
		this.funcao = funcao;
	}

	public @Pattern(regexp = "\"\\\\d{5}-\\\\d{3}\", message = \"O CEP deve estar no formato 12345-678.\"") String getCep() {
		return cep;
	}

	public void setCep(@Pattern(regexp = "\"\\\\d{5}-\\\\d{3}\", message = \"O CEP deve estar no formato 12345-678.\"") String cep) {
		this.cep = cep;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}
}
