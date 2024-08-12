package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto,Long>{
	List<Produto> findTop10ByOrderByPrecoDesc();
}
