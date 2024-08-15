package app.service;

import app.entity.Endereco;
import app.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> findById(Long id) {
        return enderecoRepository.findById(id);
    }


    public Endereco save(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }
    public String update(Endereco endereco, Long id) {
        endereco.setId(id);
        enderecoRepository.save(endereco);
        return "Endereço atualizado com sucesso!!!";
    }

    public void deleteById(Long id) {
        enderecoRepository.deleteById(id);
    }
}
