package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{
    List<Venda> findByClienteNomeContaining(String nomeCliente);
    List<Venda> findByFuncionarioNomeContaining(String nomeFuncionario);
    List<Venda> findTop10ByOrderByValorTotalDesc();
}
