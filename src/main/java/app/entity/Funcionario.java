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
	@Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "O telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.")
	private String Telefone;
	@Email (message = "Precisa estar no formato de e-mail")
	private String email;
	@CPF
	private String cpf;
    @Max(130)
	@NotNull (message = "O campo não pode estar vazio")
	private int idade;
    @NotBlank (message = "A função não pode estar vazia")
	private String funcao;

	
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

	public @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "O telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.") String getTelefone() {
		return Telefone;
	}

	public void setTelefone(@Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "O telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.") String telefone) {
		Telefone = telefone;
	}

	public @Email(message = "Precisa estar no formato de e-mail") String getEmail() {
		return email;
	}

	public void setEmail(@Email(message = "Precisa estar no formato de e-mail") String email) {
		this.email = email;
	}

	public @CPF String getCpf() {
		return cpf;
	}

	public void setCpf(@CPF String cpf) {
		this.cpf = cpf;
	}

	@Max(130)
	@NotNull(message = "O campo não pode estar vazio")
	public int getIdade() {
		return idade;
	}

	public void setIdade(@Max(130) @NotNull(message = "O campo não pode estar vazio") int idade) {
		this.idade = idade;
	}

	public @NotBlank(message = "A função não pode estar vazia") String getFuncao() {
		return funcao;
	}

	public void setFuncao(@NotBlank(message = "A função não pode estar vazia") String funcao) {
		this.funcao = funcao;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}
}
