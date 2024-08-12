package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{
	List<Venda> findByClienteNome(String nome);
	
	@Query("SELECT v FROM Venda v ORDER BY v.vlTotal DESC")
    List<Venda> findTop10ByOrderByVlTotalDesc();

}
