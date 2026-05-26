package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.ReceitaRequestDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.exception.DespesaNotFoundException;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Receita;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.ReceitaRepository;

@Service
public class ReceitaService {

    private final ReceitaRepository repository;

    public ReceitaService(ReceitaRepository repository) {
        this.repository = repository;
    }
    
        public List<Receita> findAll() {
        return repository.findAll();
    }

    public Receita incluir(ReceitaRequestDto dto) {
        validate(dto);
        var receita = new Receita (
            dto.data(),
            dto.valor()
        );
        return repository.save(receita);
    }
    
    public Receita BuscarPorId(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new DespesaNotFoundException(id));
    }

    public Receita atualizar(Long id, ReceitaRequestDto dto) {
        var receita = BuscarPorId(id);
        validate(dto);

        receita.setData(dto.data());
        receita.setValor(dto.valor());

        return repository.save(receita);
    }

    public void excluir(Long id) {
        var receita = BuscarPorId(id);
        repository.delete(receita);
    }

    private void validate(ReceitaRequestDto dto) {
        if (dto.data() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe a data da receita.");
        }
        if (dto.valor() == null || dto.valor() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe uma receita maior que zero.");
        }
    }
}
