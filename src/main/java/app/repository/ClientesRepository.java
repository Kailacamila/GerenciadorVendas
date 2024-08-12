package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.entity.Cliente;


public interface ClientesRepository {	
	
	public interface ClienteRepository extends JpaRepository<Cliente, Long>{
		
		@Query("SELECT c FROM Cliente c WHERE c.idade BETWEEN :minIdade AND :maxIdade")
	    List<Cliente> findIdade18a35(@Param("minIdade") int minIdade, @Param("maxIdade") int maxIdade);

	}

}
