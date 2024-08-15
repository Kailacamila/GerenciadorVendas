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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull (message = "O campo não pode estar vazio")
	@Pattern(regexp = "^(\\w+\\s+\\w+).*$", message = "O nome deve conter pelo menos duas palavras separadas por um espaço.")
	private String nome;
	@NotNull  (message = "o campo de e-mail não pode estar vazio")
	private String Telefone;
	@Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$")
	@CPF
	private String CPF;
	@Email   (message = "Precisa estar no formato de e-mail")
	private String email;
    @Min(0)
	@NotNull (message = "O campo não pode estar vazio")
	private int idade;
	@OneToMany(mappedBy = "cliente")
	@JsonIgnoreProperties("cliente")
	private List<Venda> vendas;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public @NotNull(message = "O campo não pode estar vazio") @Pattern(regexp = "^(\\w+\\s+\\w+).*$", message = "O nome deve conter pelo menos duas palavras separadas por um espaço.") String getNome() {
		return nome;
	}

	public void setNome(@NotNull(message = "O campo não pode estar vazio") @Pattern(regexp = "^(\\w+\\s+\\w+).*$", message = "O nome deve conter pelo menos duas palavras separadas por um espaço.") String nome) {
		this.nome = nome;
	}

	public @NotNull(message = "o campo de e-mail não pode estar vazio") String getTelefone() {
		return Telefone;
	}

	public void setTelefone(@NotNull(message = "o campo de e-mail não pode estar vazio") String telefone) {
		Telefone = telefone;
	}

	public @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$") String getCPF() {
		return CPF;
	}

	public void setCPF(@Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$") String CPF) {
		this.CPF = CPF;
	}

	public @org.hibernate.validator.constraints.br.CPF @Email(message = "Precisa estar no formato de e-mail") String getEmail() {
		return email;
	}

	public void setEmail(@org.hibernate.validator.constraints.br.CPF @Email(message = "Precisa estar no formato de e-mail") String email) {
		this.email = email;
	}

	@Min(0)
	@NotNull(message = "O campo não pode estar vazio")
	public int getIdade() {
		return idade;
	}

	public void setIdade(@Min(0) @NotNull(message = "O campo não pode estar vazio") int idade) {
		this.idade = idade;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}
}
