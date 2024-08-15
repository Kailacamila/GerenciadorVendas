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
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "O campo não pode estar vazio")
	@Pattern(regexp = "^(\\w+\\s+\\w+).*$", message = "O nome deve conter pelo menos duas palavras separadas por um espaço.")
	private String nome;

	@NotNull(message = "O campo de telefone não pode estar vazio")
	@Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "O telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.")
	private String telefone;

	@NotNull(message = "O campo de CPF não pode estar vazio")
	@CPF(message = "O CPF deve estar em um formato válido")
	private String cpf;

	@NotNull(message = "O campo de e-mail não pode estar vazio")
	@Email(message = "O e-mail deve estar no formato válido")
	private String email;

	@Min(value = 0, message = "A idade deve ser maior ou igual a 0")
	@NotNull(message = "O campo de idade não pode estar vazio")
	private Integer idade;

	@OneToMany(mappedBy = "cliente")
	@JsonIgnoreProperties("cliente")
	private List<Venda> vendas;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}
}
