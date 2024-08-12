package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entity.Produto;

public interface ProdutoRepositoy extends JpaRepository<Produto, Long>{
	   @Query("SELECT p FROM Produto p ORDER BY p.preco DESC")
	    List<Produto> findTop10ByOrderByPrecoDesc();

}