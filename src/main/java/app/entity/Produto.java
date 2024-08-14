package app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotEmpty(message = "O nome do produto é obrigatório.")
	private String nome;
	private String descricao;
	@NotNull (message = "O campo não pode estar vazio")
	private double preco;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public @NotEmpty(message = "O nome do produto é obrigatório.") String getNome() {
		return nome;
	}

	public void setNome(@NotEmpty(message = "O nome do produto é obrigatório.") String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@NotNull(message = "O campo não pode estar vazio")
	public double getPreco() {
		return preco;
	}

	public void setPreco(@NotNull(message = "O campo não pode estar vazio") double preco) {
		this.preco = preco;
	}
}
