package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.DespesaRequestDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.exception.DespesaNotFoundException;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Despesa;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.DespesaRepository;

@Service
public class DespesaService {
    
    private final DespesaRepository repository;

    public DespesaService(DespesaRepository repository) {
        this.repository = repository;
    }
    
    public List<Despesa> findAll() {
        return repository.findAll();
    }

    public Despesa incluir(DespesaRequestDto dto) {
        validate(dto);
        var despesa = new Despesa(
            dto.nome(),
            dto.valor(),
            dto.data(),
            dto.categoria()
        );
        return repository.save(despesa);
    }
    
    public Despesa BuscarPorId(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new DespesaNotFoundException(id));
    }

    public Despesa atualizar(Long id, DespesaRequestDto dto) {
        var despesa = BuscarPorId(id);
        validate(dto);

        despesa.setNome(dto.nome());
        despesa.setValor(dto.valor());
        despesa.setCategoria(dto.categoria());

        return repository.save(despesa);
    }

    public void excluir(Long id) {
        var despesa = BuscarPorId(id);
        repository.delete(despesa);
    }

    private void validate(DespesaRequestDto dto) {
        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o nome da despesa.");
        }
        if (dto.valor() == null || dto.valor() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe uma despesa maior que zero.");
        }
        if (dto.data() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe a data da despesa.");
        }
    }
}
